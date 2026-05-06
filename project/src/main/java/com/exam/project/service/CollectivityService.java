package com.exam.project.service;

import com.exam.project.model.Collectivity;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectivityService {
    private final DataSource dataSource;
    private final MemberService memberService;

    public CollectivityService(DataSource dataSource, MemberService memberService) {
        this.dataSource = dataSource;
        this.memberService = memberService;
    }

    public Collectivity createCollectivity(Collectivity col) throws SQLException {
        String sql = "INSERT INTO collectivity (id, name, location, creation_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, col.getId());
            ps.setString(2, col.getName());
            ps.setString(3, col.getLocation());
            ps.setDate(4, Date.valueOf(col.getCreationDate()));
            ps.executeUpdate();
        }
        return col;
    }

    public Collectivity getCollectivityById(String id) throws SQLException {
        String sql = "SELECT id, name, location, creation_date, identification_number, unique_name FROM collectivity WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Collectivity col = new Collectivity();
                col.setId(rs.getString("id"));
                col.setName(rs.getString("name"));
                col.setLocation(rs.getString("location"));
                Date creationDate = rs.getDate("creation_date");
                if (creationDate != null) col.setCreationDate(creationDate.toLocalDate());
                col.setIdentificationNumber(rs.getString("identification_number"));
                col.setUniqueName(rs.getString("unique_name"));
                col.setMembers(memberService.getMembersByCollectivityId(id));
                return col;
            }
        }
        return null;
    }

    public List<Collectivity> getAllCollectivities() throws SQLException {
        List<Collectivity> collectivities = new ArrayList<>();
        String sql = "SELECT id, name, location, creation_date, identification_number, unique_name FROM collectivity ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Collectivity col = new Collectivity();
                col.setId(rs.getString("id"));
                col.setName(rs.getString("name"));
                col.setLocation(rs.getString("location"));
                Date creationDate = rs.getDate("creation_date");
                if (creationDate != null) col.setCreationDate(creationDate.toLocalDate());
                col.setIdentificationNumber(rs.getString("identification_number"));
                col.setUniqueName(rs.getString("unique_name"));
                col.setMembers(memberService.getMembersByCollectivityId(col.getId()));
                collectivities.add(col);
            }
        }
        return collectivities;
    }

    public Collectivity assignIdentity(String id, String number, String name) throws SQLException {
        Collectivity col = getCollectivityById(id);
        if (col == null) {
            throw new IllegalArgumentException("Collectivity not found with id: " + id);
        }

        if (col.getIdentificationNumber() != null || col.getUniqueName() != null) {
            throw new IllegalStateException("Identity already established.");
        }

        String sql = "UPDATE collectivity SET identification_number = ?, unique_name = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setString(2, name);
            ps.setString(3, id);
            ps.executeUpdate();
        }

        return getCollectivityById(id);
    }
}