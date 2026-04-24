package com.exam.project.service;

import com.exam.project.model.Collectivity;
import com.exam.project.model.Member;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
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

    public void createCollectivity(Collectivity col) throws SQLException {
        String sql = "INSERT INTO collectivity (id, name, city, creation_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, col.getId());
            ps.setString(2, col.getName());
            ps.setString(3, col.getCity());
            ps.setDate(4, Date.valueOf(col.getCreationDate()));
            ps.executeUpdate();
        }
    }

    public Collectivity getCollectivityById(String id) throws SQLException {
        String sql = "SELECT id, name, city, creation_date, identification_number, unique_name FROM collectivity WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Collectivity col = new Collectivity();
                col.setId(rs.getString("id"));
                col.setName(rs.getString("name"));
                col.setCity(rs.getString("city"));
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
        String sql = "SELECT id, name, city, creation_date, identification_number, unique_name FROM collectivity ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Collectivity col = new Collectivity();
                col.setId(rs.getString("id"));
                col.setName(rs.getString("name"));
                col.setCity(rs.getString("city"));
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

    public void assignIdentity(String id, String number, String name) throws SQLException {
        String sql = "UPDATE collectivity SET identification_number = ?, unique_name = ? WHERE id = ? AND identification_number IS NULL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setString(2, name);
            ps.setString(3, id);
            if (ps.executeUpdate() == 0) throw new IllegalStateException("Identité déjà fixée.");
        }
    }
}