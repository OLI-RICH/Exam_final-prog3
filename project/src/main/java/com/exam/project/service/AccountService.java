package com.exam.project.service;

import com.exam.project.model.Account;
import com.exam.project.model.AccountType;
import com.exam.project.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccount(String id) throws SQLException {
        return accountRepository.findById(id);
    }

    public void createAccount(Account account) throws SQLException {
        if (account.getType() == AccountType.CASH && accountRepository.hasCashAccount(account.getOwnerId())) {
            throw new IllegalArgumentException("A fund already exists for this community.");
        }

        if (account.getType() == AccountType.BANK && account.getAccountNumber() != null) {
            if (account.getAccountNumber().length() != 23 || !account.getAccountNumber().matches("\\d{23}")) {
                throw new IllegalArgumentException("The bank account details (RIB) must contain exactly 23 digits.");
            }
        }
        accountRepository.save(account);
    }

    public List<Account> getAccountsByOwner(String ownerId) throws SQLException {
        return accountRepository.findByOwnerId(ownerId);
    }

    public void updateBalance(String accountId, BigDecimal newBalance) throws SQLException {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The balance cannot be negative.");
        }
        accountRepository.updateBalance(accountId, newBalance);
    }
}