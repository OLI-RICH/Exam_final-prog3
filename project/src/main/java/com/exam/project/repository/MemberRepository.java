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
}