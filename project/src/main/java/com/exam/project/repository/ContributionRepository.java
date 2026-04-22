package com.exam.project.repository;

import com.exam.project.model.Contribution;
import com.exam.project.model.PaymentMethod;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContributionRepository {
    private final Connection connection;

    public ContributionRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Contribution contribution) throws SQLException {
        String sql = "INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contribution.getId());
            pstmt.setString(2, contribution.getMemberId());
            pstmt.setString(3, contribution.getCollectivityId());
            pstmt.setBigDecimal(4, contribution.getAmount());
            pstmt.setDate(5, Date.valueOf(contribution.getDate()));
            pstmt.setString(6, contribution.getPaymentMethod().name());
            pstmt.setString(7, contribution.getDescription());
            pstmt.executeUpdate();
        }
    }

    public Optional<Contribution> findById(String id) throws SQLException {
        String sql = "SELECT * FROM contribution WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToContribution(rs));
            }
        }
        return Optional.empty();
    }

    public List<Contribution> findByCollectivityId(String collectivityId) throws SQLException {
        String sql = "SELECT * FROM contribution WHERE collectivity_id = ? ORDER BY date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, collectivityId);
            ResultSet rs = pstmt.executeQuery();
            List<Contribution> contributions = new ArrayList<>();
            while (rs.next()) {
                contributions.add(mapToContribution(rs));
            }
            return contributions;
        }
    }

    public List<Contribution> findByMemberId(String memberId) throws SQLException {
        String sql = "SELECT * FROM contribution WHERE member_id = ? ORDER BY date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            List<Contribution> contributions = new ArrayList<>();
            while (rs.next()) {
                contributions.add(mapToContribution(rs));
            }
            return contributions;
        }
    }

    private Contribution mapToContribution(ResultSet rs) throws SQLException {
        Contribution contribution = new Contribution();
        contribution.setId(rs.getString("id"));
        contribution.setMemberId(rs.getString("member_id"));
        contribution.setCollectivityId(rs.getString("collectivity_id"));
        contribution.setAmount(rs.getBigDecimal("amount"));
        contribution.setDate(rs.getDate("date").toLocalDate());
        contribution.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
        contribution.setDescription(rs.getString("description"));
        return contribution;
    }
}