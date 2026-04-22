package com.exam.project.repository;

import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;

@Repository
public class MemberRepository {
    private final Connection connection;

    public MemberRepository(Connection connection) {
        this.connection = connection;
    }

    public long countSeniors(List<String> ids) throws SQLException {
        if (ids == null || ids.isEmpty()) return 0;

        String sql = "SELECT COUNT(*) FROM member WHERE id = ANY(?) AND joining_date <= CURRENT_DATE - INTERVAL '6 months'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Array array = connection.createArrayOf("VARCHAR", ids.toArray());
            pstmt.setArray(1, array);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        }
    }

    public void updateCollectivityIdentity(String id, String number, String name) throws SQLException {
        String sql = "UPDATE collectivity SET identification_number = ?, unique_name = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        }
    }

    public boolean isIdentityAlreadySet(String id) throws SQLException {
        String sql = "SELECT identification_number FROM collectivity WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getString("identification_number") != null;
        }
    }
}