package com.exam.project.service;

import com.exam.project.model.Account;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final DataSource dataSource;

    public AccountService(DataSource dataSource) {
        this.dataSource = dataSource;
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
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account ORDER BY owner_id, type";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
}
