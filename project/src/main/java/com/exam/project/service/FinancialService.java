package com.exam.project.service;

import com.exam.project.dto.MemberStatisticDTO;
import com.exam.project.dto.CollectivityStatisticDTO;
import com.exam.project.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialService {
    private final AccountService accountService;
    private final ContributionService contributionService;
    private final JdbcTemplate jdbcTemplate;

    public FinancialService(AccountService accountService, ContributionService contributionService, JdbcTemplate jdbcTemplate) {
        this.accountService = accountService;
        this.contributionService = contributionService;
        this.jdbcTemplate = jdbcTemplate;
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
        String sql =
                "SELECT m.id, m.first_name, m.last_name, " +
                        "       COALESCE(SUM(CASE WHEN c.date BETWEEN ? AND ? THEN c.amount ELSE 0 END), 0) as total_paid, " +
                        "       COALESCE(" +
                        "           (SELECT SUM(req.amount) FROM contribution req " +
                        "            WHERE req.collectivity_id = ? AND req.status = 'ACTIVE' AND req.member_id IS NULL) " + // Cotisation de référence active
                        "           - COALESCE(SUM(c.amount), 0), 0" +
                        "       ) as potential_unpaid " +
                        "FROM member m " +
                        "LEFT JOIN contribution c ON m.id = c.member_id AND c.collectivity_id = ? " +
                        "WHERE m.collectivity_id = ? " +
                        "GROUP BY m.id, m.first_name, m.last_name";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new MemberStatisticDTO(
                rs.getString("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getBigDecimal("total_paid"),
                rs.getBigDecimal("potential_unpaid")
        ), start, end, collectivityId, collectivityId, collectivityId);
    }

    public List<CollectivityStatisticDTO> getGlobalStatistics(LocalDate start, LocalDate end) {
        String sql =
                "SELECT col.id, col.name, " +
                        "       -- Nombre de nouveaux adhérents inscrits sur la période " +
                        "       (SELECT COUNT(*) FROM member m WHERE m.collectivity_id = col.id AND m.joining_date BETWEEN ? AND ?) as new_members, " +
                        "       -- Pourcentage de membres à jour (sur les cotisations actives uniquement) " +
                        "       (SELECT " +
                        "           CASE WHEN COUNT(m2.id) = 0 THEN 0.0 " +
                        "           ELSE (SUM(CASE WHEN " +
                        "               COALESCE((SELECT SUM(pay.amount) FROM contribution pay WHERE pay.member_id = m2.id), 0) >= " +
                        "               COALESCE((SELECT SUM(req.amount) FROM contribution req WHERE req.collectivity_id = col.id AND req.status = 'ACTIVE' AND req.member_id IS NULL), 0) " +
                        "               THEN 1 ELSE 0 END) * 100.0) / COUNT(m2.id) " +
                        "           END " +
                        "        FROM member m2 WHERE m2.collectivity_id = col.id) as uptodate_pct " +
                        "FROM collectivity col";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new CollectivityStatisticDTO(
                rs.getString("id"),
                rs.getString("name"),
                rs.getDouble("uptodate_pct"),
                rs.getLong("new_members")
        ), start, end);
    }
}