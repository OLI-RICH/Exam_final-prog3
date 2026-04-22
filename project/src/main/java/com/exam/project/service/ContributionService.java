package com.exam.project.service;

import com.exam.project.model.Account;
import com.exam.project.model.Contribution;
import com.exam.project.repository.ContributionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        if (contribution.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant de la cotisation doit être positif.");
        }

        // 1. Sauvegarde de la transaction en base
        contributionRepository.save(contribution);

        // 2. Mise à jour du solde du compte de la collectivité
        List<Account> accounts = accountService.getAccountsByOwner(contribution.getCollectivityId());

        // On cherche le compte qui correspond au mode de paiement (CASH -> compte CASH, etc.)
        Account targetAccount = accounts.stream()
                .filter(acc -> acc.getType().name().equals(contribution.getPaymentMethod().name().replace("_TRANSFER", "")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucun compte de type " + contribution.getPaymentMethod() + " configuré pour cette collectivité."));

        BigDecimal newBalance = targetAccount.getBalance().add(contribution.getAmount());
        accountService.updateBalance(targetAccount.getId(), newBalance);
    }

    public Optional<Contribution> getContribution(String id) throws SQLException {
        return contributionRepository.findById(id);
    }

    public List<Contribution> getContributionsByCollectivity(String collectivityId) throws SQLException {
        return contributionRepository.findByCollectivityId(collectivityId);
    }

    public List<Contribution> getContributionsByMember(String memberId) throws SQLException {
        return contributionRepository.findByMemberId(memberId);
    }
}