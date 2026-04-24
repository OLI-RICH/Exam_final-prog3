package com.exam.project.service;

import com.exam.project.model.Contribution;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContributionService {
    private final DataSource dataSource;

    public ContributionService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void recordContribution(Contribution c) throws SQLException {
        String sql = "INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getMemberId());
            ps.setString(3, c.getCollectivityId());
            ps.setBigDecimal(4, c.getAmount());
            ps.setDate(5, Date.valueOf(c.getDate()));
            ps.setString(6, c.getPaymentMethod());
            ps.setString(7, c.getDescription());
            ps.executeUpdate();
        }
    }

    public List<Contribution> getMembershipFeesByCollectivityId(String collectivityId) throws SQLException {
        List<Contribution> fees = new ArrayList<>();
        String sql = "SELECT * FROM contribution WHERE collectivity_id = ? AND (member_id IS NULL OR member_id = '')";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contribution c = new Contribution();
                c.setId(rs.getString("id"));
                c.setCollectivityId(rs.getString("collectivity_id"));
                c.setAmount(rs.getBigDecimal("amount"));
                Date date = rs.getDate("date");
                if (date != null) c.setDate(date.toLocalDate());
                c.setDescription(rs.getString("description"));
                fees.add(c);
            }
        }
        return fees;
    }

    public List<Contribution> getTransactionsByCollectivityId(String collectivityId, LocalDate start, LocalDate end) throws SQLException {
        List<Contribution> transactions = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM contribution WHERE collectivity_id = ? AND member_id IS NOT NULL AND member_id != ''");
        if (start != null) sql.append(" AND date >= ?");
        if (end != null) sql.append(" AND date <= ?");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setString(index++, collectivityId);
            if (start != null) ps.setDate(index++, Date.valueOf(start));
            if (end != null) ps.setDate(index++, Date.valueOf(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contribution c = new Contribution();
                c.setId(rs.getString("id"));
                c.setMemberId(rs.getString("member_id"));
                c.setCollectivityId(rs.getString("collectivity_id"));
                c.setAmount(rs.getBigDecimal("amount"));
                Date date = rs.getDate("date");
                if (date != null) c.setDate(date.toLocalDate());
                c.setPaymentMethod(rs.getString("payment_method"));
                c.setDescription(rs.getString("description"));
                transactions.add(c);
            }
        }
        return transactions;
    }

    public BigDecimal getTotalContributions(String collectivityId, LocalDate at) throws SQLException {
        String sqlSum = "SELECT COALESCE(SUM(amount), 0) FROM contribution WHERE collectivity_id = ? AND date <= ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement psSum = conn.prepareStatement(sqlSum)) {
            psSum.setString(1, collectivityId);
            psSum.setDate(2, Date.valueOf(at));
            ResultSet rsSum = psSum.executeQuery();
            if (rsSum.next()) {
                return rsSum.getBigDecimal(1);
            }
        }
        return BigDecimal.ZERO;
    }
}