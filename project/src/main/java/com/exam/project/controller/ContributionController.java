package com.exam.project.controller;

import com.exam.project.model.Contribution;
import com.exam.project.service.ContributionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {
    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping
    public ResponseEntity<?> createContribution(@RequestBody Contribution request) {
        try {
            if (request.getId() == null) request.setId("CON-" + UUID.randomUUID().toString().substring(0, 8));
            if (request.getDate() == null) request.setDate(LocalDate.now());
            contributionService.recordContribution(request);
            return ResponseEntity.ok("Contribution recorded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
