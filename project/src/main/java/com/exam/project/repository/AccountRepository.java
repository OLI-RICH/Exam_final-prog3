package com.exam.project.repository;

import com.exam.project.model.Account;
import com.exam.project.model.AccountType;
import com.exam.project.model.BankName;
import com.exam.project.model.MobileMoneyService;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Account account) throws SQLException {
        String sql = "INSERT INTO account (id, type, owner_id, balance, holder_name, bank_name, account_number, mobile_service, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, account.getId());
            pstmt.setString(2, account.getType().name());
            pstmt.setString(3, account.getOwnerId());
            pstmt.setBigDecimal(4, account.getBalance());
            pstmt.setString(5, account.getHolderName());
            
            pstmt.setString(6, account.getBankName() != null ? account.getBankName().name() : null);
            pstmt.setString(7, account.getAccountNumber());
            pstmt.setString(8, account.getMobileService() != null ? account.getMobileService().name() : null);
            pstmt.setString(9, account.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    public Optional<Account> findById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToAccount(rs));
            }
        }
        return Optional.empty();
    }

    public List<Account> findByOwnerId(String ownerId) throws SQLException {
        String sql = "SELECT * FROM account WHERE owner_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(mapToAccount(rs));
            }
            return accounts;
        }
    }

    public void updateBalance(String id, java.math.BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, newBalance);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        }
    }

    public boolean hasCashAccount(String ownerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM account WHERE owner_id = ? AND type = 'CASH'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private Account mapToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getString("id"));
        account.setType(AccountType.valueOf(rs.getString("type")));
        account.setOwnerId(rs.getString("owner_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setHolderName(rs.getString("holder_name"));
        String bankName = rs.getString("bank_name");
        if (bankName != null) {
            account.setBankName(BankName.valueOf(bankName));
        }
        account.setAccountNumber(rs.getString("account_number"));
        String mobileService = rs.getString("mobile_service");
        if (mobileService != null) {
            account.setMobileService(MobileMoneyService.valueOf(mobileService));
        }
        account.setPhoneNumber(rs.getString("phone_number"));
        return account;
    }

    public List<Account> findAccountsByOwnerAtDate(String ownerId, LocalDate at) throws SQLException {
        String sql = "SELECT a.*, " +
                "(SELECT COALESCE(SUM(c.amount), 0) FROM contribution c " +
                " WHERE c.collectivity_id = a.owner_id AND c.date <= ?) as dynamic_balance " +
                "FROM account a WHERE a.owner_id = ?";

        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(at));
            pstmt.setString(2, ownerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Account acc = mapToAccount(rs);
                acc.setBalance(rs.getBigDecimal("dynamic_balance"));
                accounts.add(acc);
            }
        }
        return accounts;
    }
}