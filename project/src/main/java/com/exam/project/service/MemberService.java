package com.exam.project.service;

import com.exam.project.model.Contribution;
import com.exam.project.model.Member;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final DataSource dataSource;

    public MemberService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addMember(Member m) throws SQLException {
        String sql = "INSERT INTO member (id, first_name, last_name, collectivity_id, joining_date, occupation) VALUES (?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getId());
            ps.setString(2, m.getFirstName());
            ps.setString(3, m.getLastName());
            ps.setString(4, m.getCollectivityId());
            ps.setDate(5, Date.valueOf(m.getJoinDate()));
            ps.setString(6, m.getStatus());
            ps.executeUpdate();
        }
    }

    public Member getMemberById(String id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, collectivity_id, joining_date, occupation FROM member WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member m = new Member();
                m.setId(rs.getString("id"));
                m.setFirstName(rs.getString("first_name"));
                m.setLastName(rs.getString("last_name"));
                m.setCollectivityId(rs.getString("collectivity_id"));
                Date joinDate = rs.getDate("joining_date");
                if (joinDate != null) m.setJoinDate(joinDate.toLocalDate());
                m.setStatus(rs.getString("occupation"));
                return m;
            }
        }
        return null;
    }

    public List<Member> getMembersByCollectivityId(String collectivityId) throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, collectivity_id, joining_date, occupation FROM member WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getString("id"));
                m.setFirstName(rs.getString("first_name"));
                m.setLastName(rs.getString("last_name"));
                m.setCollectivityId(rs.getString("collectivity_id"));
                Date joinDate = rs.getDate("joining_date");
                if (joinDate != null) m.setJoinDate(joinDate.toLocalDate());
                m.setStatus(rs.getString("occupation"));
                members.add(m);
            }
        }
        return members;
    }

    public void recordPayment(String memberId, Contribution payment) throws SQLException {
        payment.setMemberId(memberId);
        String sql = "INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getId());
            ps.setString(2, memberId);
            ps.setString(3, payment.getCollectivityId());
            ps.setBigDecimal(4, payment.getAmount());
            ps.setDate(5, Date.valueOf(payment.getDate()));
            ps.setString(6, payment.getPaymentMethod());
            ps.setString(7, payment.getDescription());
            ps.executeUpdate();
        }
    }
}