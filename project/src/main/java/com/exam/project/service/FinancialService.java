package com.exam.project.service;

import com.exam.project.model.Account;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialService {
    private final AccountService accountService;
    private final ContributionService contributionService;

    public FinancialService(AccountService accountService, ContributionService contributionService) {
        this.accountService = accountService;
        this.contributionService = contributionService;
    }

    public List<Account> getAccountsWithBalance(String collectivityId, LocalDate at) throws SQLException {
        List<Account> accounts = accountService.getAccountsByOwner(collectivityId);
        BigDecimal totalContributions = contributionService.getTotalContributions(collectivityId, at);
        for (Account account : accounts) {
            account.setBalance(totalContributions);
        }
        return accounts;
    }
}