package com.exam.project.service;

import com.exam.project.model.*;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FederationService {
    private final DataSource dataSource;

    public FederationService(DataSource dataSource) {
        this.dataSource = dataSource;
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
                col.setMembers(getMembersByCollectivityId(id));
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
                col.setMembers(getMembersByCollectivityId(col.getId()));
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

    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account (id, type, owner_id, balance, holder_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getId());
            ps.setString(2, account.getType());
            ps.setString(3, account.getOwnerId());
            ps.setBigDecimal(4, account.getBalance());
            ps.setString(5, account.getHolderName());
            ps.executeUpdate();
        }
    }

    public Account getAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Account.builder()
                        .id(rs.getString("id"))
                        .type(rs.getString("type"))
                        .ownerId(rs.getString("owner_id"))
                        .balance(rs.getBigDecimal("balance"))
                        .holderName(rs.getString("holder_name"))
                        .build();
            }
        }
        return null;
    }

    public List<Account> getAccountsByOwner(String ownerId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE owner_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(Account.builder()
                        .id(rs.getString("id"))
                        .type(rs.getString("type"))
                        .ownerId(rs.getString("owner_id"))
                        .balance(rs.getBigDecimal("balance"))
                        .holderName(rs.getString("holder_name"))
                        .build());
            }
        }
        return accounts;
    }

    public List<Account> getAccountsWithBalance(String collectivityId, LocalDate at) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        BigDecimal totalContributions = getTotalContributions(collectivityId, at);
        String sqlAcc = "SELECT id, type, owner_id, balance, holder_name FROM account WHERE owner_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement psAcc = conn.prepareStatement(sqlAcc)) {
            psAcc.setString(1, collectivityId);
            ResultSet rs = psAcc.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getString("id"));
                account.setType(rs.getString("type"));
                account.setOwnerId(rs.getString("owner_id"));
                account.setBalance(totalContributions);
                account.setHolderName(rs.getString("holder_name"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    private BigDecimal getTotalContributions(String collectivityId, LocalDate at) throws SQLException {
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
}