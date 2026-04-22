package com.exam.project.repository;

import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class CollectivityRepository {
    private final Connection connection;

    public CollectivityRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean isIdentityAlreadySet(String id) throws SQLException {
        String sql = "SELECT identification_number FROM collectivity WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getString("identification_number") != null;
        }
    }

    public boolean uniqueNameExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM collectivity WHERE unique_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void updateIdentity(String id, String number, String name) throws SQLException {
        String sql = "UPDATE collectivity SET identification_number = ?, unique_name = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        }
    }
}