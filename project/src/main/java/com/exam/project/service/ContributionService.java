package com.exam.project.service;

import com.exam.project.model.Account;
import com.exam.project.model.Contribution;
import com.exam.project.repository.ContributionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    private final ContributionRepository contributionRepository;
    private final AccountService accountService;

    public ContributionService(ContributionRepository contributionRepository, AccountService accountService) {
        this.contributionRepository = contributionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public void recordContribution(Contribution contribution) throws SQLException {
        if (contribution.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be positive");
        }

        contributionRepository.save(contribution);

        List<Account> accounts = accountService.getAccountsByOwner(contribution.getCollectivityId());

        Account targetAccount = accounts.stream()
                .filter(acc -> acc.getType().name().equals(contribution.getPaymentMethod().name().replace("_TRANSFER", "")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No account found for this payment method"));

        java.math.BigDecimal newBalance = targetAccount.getBalance().add(contribution.getAmount());
        accountService.updateBalance(targetAccount.getId(), newBalance);
    }

    public List<Contribution> getContributionsByCollectivity(String collectivityId) throws SQLException {
        return contributionRepository.findByCollectivityId(collectivityId);
    }

    public List<Contribution> getContributionsByMember(String memberId) throws SQLException {
        return contributionRepository.findByMemberId(memberId);
    }

    public Optional<Contribution> getContribution(String id) throws SQLException {
        return contributionRepository.findById(id);
    }
}