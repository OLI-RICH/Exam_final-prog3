package com.exam.project.service;

import com.exam.project.dto.AttendanceRecord;
import com.exam.project.dto.AttendeeDTO;
import com.exam.project.model.Activity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
    private final DataSource dataSource;

    public ActivityService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addActivities(String collectivityId, List<Activity> activities) {
        String sql = "INSERT INTO activity (id, collectivity_id, title, description, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Activity act : activities) {
                String id = "ACT-" + UUID.randomUUID().toString().substring(0, 5);
                ps.setString(1, id);
                ps.setString(2, collectivityId);
                ps.setString(3, act.getTitle());
                ps.setString(4, act.getDescription());
                ps.setDate(5, Date.valueOf(act.getDate() != null ? act.getDate() : LocalDate.now()));
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding activities : " + e.getMessage(), e);
        }
    }

    public List<Activity> getActivities(String collectivityId) {
        List<Activity> list = new ArrayList<>();
        String sql = "SELECT id, title, description, date FROM activity WHERE collectivity_id = ? ORDER BY date DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Activity(
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("date").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving activities : " + e.getMessage(), e);
        }
        return list;
    }

    public void recordAttendance(String activityId, List<AttendanceRecord> records) {
        String checkSql = "SELECT COUNT(*) FROM attendance WHERE activity_id = ?";
        String insertSql = "INSERT INTO attendance (activity_id, member_id, status) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setString(1, activityId);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new IllegalStateException("Attendance for this activity has already been recorded and cannot be changed!");
                    }
                }
            }

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                for (AttendanceRecord rec : records) {
                    insertPs.setString(1, activityId);
                    insertPs.setString(2, rec.getMemberId());
                    insertPs.setString(3, rec.getStatus().toUpperCase());
                    insertPs.addBatch();
                }
                insertPs.executeBatch();
            }
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error recording attendance : " + e.getMessage(), e);
        }
    }

    public List<AttendeeDTO> getAttendance(String activityId) {
        List<AttendeeDTO> attendees = new ArrayList<>();
        String sql = "SELECT m.id, m.first_name, m.last_name, a.status " +
                "FROM attendance a " +
                "JOIN member m ON a.member_id = m.id " +
                "WHERE a.activity_id = ? AND a.status = 'PRESENT'";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    attendees.add(new AttendeeDTO(
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendance data : " + e.getMessage(), e);
        }
        return attendees;
    }
}