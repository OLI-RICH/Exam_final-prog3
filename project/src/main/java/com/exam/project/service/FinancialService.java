package com.exam.project.service;

import com.exam.project.dto.MemberStatisticDTO;
import com.exam.project.dto.CollectivityStatisticDTO;
import com.exam.project.model.Account;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialService {
    private final AccountService accountService;
    private final ContributionService contributionService;
    private final DataSource dataSource;

    public FinancialService(AccountService accountService, ContributionService contributionService, DataSource dataSource) {
        this.accountService = accountService;
        this.contributionService = contributionService;
        this.dataSource = dataSource;
    }

    public List<Account> getAccountsWithBalance(String collectivityId, LocalDate at) throws SQLException {
        List<Account> accounts = accountService.getAccountsByOwner(collectivityId);
        BigDecimal totalContributions = contributionService.getTotalContributions(collectivityId, at);
        for (Account account : accounts) {
            account.setBalance(totalContributions);
        }
        return accounts;
    }

    public List<MemberStatisticDTO> getMemberStatistics(String collectivityId, LocalDate start, LocalDate end) {
        List<MemberStatisticDTO> stats = new ArrayList<>();
        String sql =
                "SELECT m.id, m.first_name, m.last_name, " +
                        "       COALESCE(SUM(CASE WHEN c.date BETWEEN ? AND ? THEN c.amount ELSE 0 END), 0) as total_paid, " +
                        "       COALESCE(" +
                        "           (SELECT SUM(req.amount) FROM contribution req " +
                        "            WHERE req.collectivity_id = ? AND req.status = 'ACTIVE' AND req.member_id IS NULL) " +
                        "           - COALESCE(SUM(c.amount), 0), 0" +
                        "       ) as potential_unpaid, " +
                        "       COALESCE(" +
                        "           (SELECT (COUNT(CASE WHEN att.status = 'PRESENT' THEN 1 END) * 100.0) / NULLIF(COUNT(*), 0) " +
                        "            FROM attendance att " +
                        "            JOIN activity act ON att.activity_id = act.id " +
                        "            WHERE att.member_id = m.id AND act.date BETWEEN ? AND ?), 0.0" +
                        "       ) as attendance_rate " +
                        "FROM member m " +
                        "LEFT JOIN contribution c ON m.id = c.member_id AND c.collectivity_id = ? " +
                        "WHERE m.collectivity_id = ? " +
                        "GROUP BY m.id, m.first_name, m.last_name";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ps.setString(3, collectivityId);
            ps.setDate(4, Date.valueOf(start));
            ps.setDate(5, Date.valueOf(end));
            ps.setString(6, collectivityId);
            ps.setString(7, collectivityId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal totalPaid = rs.getBigDecimal("total_paid");
                    BigDecimal potentialUnpaid = rs.getBigDecimal("potential_unpaid");
                    BigDecimal attRateVal = rs.getBigDecimal("attendance_rate");

                    stats.add(new MemberStatisticDTO(
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            totalPaid != null ? totalPaid : BigDecimal.ZERO,
                            potentialUnpaid != null ? potentialUnpaid : BigDecimal.ZERO,
                            attRateVal != null ? attRateVal.doubleValue() : 0.0
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving member statistics : " + e.getMessage(), e);
        }
        return stats;
    }

    public List<CollectivityStatisticDTO> getGlobalStatistics(LocalDate start, LocalDate end) {
        List<CollectivityStatisticDTO> stats = new ArrayList<>();
        String sql =
                "SELECT col.id, col.name, " +
                        "       (SELECT COUNT(*) FROM member m WHERE m.collectivity_id = col.id AND m.joining_date BETWEEN ? AND ?) as new_members, " +
                        "       (SELECT " +
                        "           CASE WHEN COUNT(m2.id) = 0 THEN 0.0 " +
                        "           ELSE (SUM(CASE WHEN " +
                        "               COALESCE((SELECT SUM(pay.amount) FROM contribution pay WHERE pay.member_id = m2.id), 0) >= " +
                        "               COALESCE((SELECT SUM(req.amount) FROM contribution req WHERE req.collectivity_id = col.id AND req.status = 'ACTIVE' AND req.member_id IS NULL), 0) " +
                        "               THEN 1 ELSE 0 END) * 100.0) / COUNT(m2.id) " +
                        "           END " +
                        "        FROM member m2 WHERE m2.collectivity_id = col.id) as uptodate_pct, " +
                        "       COALESCE(" +
                        "           (SELECT (COUNT(CASE WHEN att.status = 'PRESENT' THEN 1 END) * 100.0) / NULLIF(COUNT(*), 0) " +
                        "            FROM attendance att " +
                        "            JOIN activity act ON att.activity_id = act.id " +
                        "            JOIN member m3 ON att.member_id = m3.id " +
                        "            WHERE m3.collectivity_id = col.id AND act.date BETWEEN ? AND ?), 0.0" +
                        "       ) as global_attendance_rate " +
                        "FROM collectivity col";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ps.setDate(3, Date.valueOf(start));
            ps.setDate(4, Date.valueOf(end));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal pctVal = rs.getBigDecimal("uptodate_pct");
                    BigDecimal attRateVal = rs.getBigDecimal("global_attendance_rate");

                    double percentage = pctVal != null ? pctVal.doubleValue() : 0.0;
                    double globalAttRate = attRateVal != null ? attRateVal.doubleValue() : 0.0;

                    stats.add(new CollectivityStatisticDTO(
                            rs.getString("id"),
                            rs.getString("name"),
                            percentage,
                            rs.getLong("new_members"),
                            globalAttRate
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving global statistics : " + e.getMessage(), e);
        }
        return stats;
    }
}