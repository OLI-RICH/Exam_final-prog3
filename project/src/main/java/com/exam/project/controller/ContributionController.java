package com.exam.project.controller;

import com.exam.project.dto.CreateContribution;
import com.exam.project.model.Contribution;
import com.exam.project.service.ContributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping
    public ResponseEntity<String> recordContribution(@RequestBody CreateContribution request) {
        try {
            Contribution contribution = new Contribution();
            contribution.setId("CONTRIB-" + UUID.randomUUID().toString().substring(0, 8));
            contribution.setMemberId(request.getMemberId());
            contribution.setCollectivityId(request.getCollectivityId());
            contribution.setAmount(request.getAmount());
            contribution.setDate(request.getDate());
            contribution.setPaymentMethod(request.getPaymentMethod());
            contribution.setDescription(request.getDescription());

            contributionService.recordContribution(contribution);
            return ResponseEntity.ok("Cotisation enregistrée et solde mis à jour.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContribution(@PathVariable String id) {
        try {
            return contributionService.getContribution(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/collectivity/{collectivityId}")
    public ResponseEntity<?> getContributionsByCollectivity(@PathVariable String collectivityId) {
        try {
            List<Contribution> contributions = contributionService.getContributionsByCollectivity(collectivityId);
            return ResponseEntity.ok(contributions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getContributionsByMember(@PathVariable String memberId) {
        try {
            List<Contribution> contributions = contributionService.getContributionsByMember(memberId);
            return ResponseEntity.ok(contributions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}