package com.exam.project.controller;

import com.exam.project.service.FederationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FederationController {

    private final FederationService federationService;

    public FederationController(FederationService federationService) {
        this.federationService = federationService;
    }

    @PostMapping("/collectivities")
    public ResponseEntity<String> open(@RequestBody(required = false) Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            body = Map.of(
                    "id", "COLL-DEFAULT-01",
                    "name", "Collectivité Test Automatique",
                    "federationApproval", true,
                    "members", List.of(
                            "M-SENIOR-1", "M-SENIOR-2", "M-SENIOR-3", "M-SENIOR-4", "M-SENIOR-5",
                            "M-JUNIOR-1", "M-JUNIOR-2", "M-JUNIOR-3", "M-JUNIOR-4", "M-JUNIOR-5"
                    )
            );
        }

        try {
            if (federationService.validateCollectivityA(body)) {
                return ResponseEntity.status(201).body("Opening accepted (Default data used or received).");
            }
            return ResponseEntity.status(400).body("Rejected: Rule A criteria not met (Check seniors in database).");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/members")
    public ResponseEntity<String> join(@RequestBody(required = false) Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            body = Map.of(
                    "name", "Nouveau Membre Auto",
                    "godparents", List.of("M-SENIOR-1", "M-SENIOR-2"),
                    "payment", 50000
            );
        }

        try {
            if (federationService.validateNewMemberB2(body)) {
                return ResponseEntity.status(201).body("Membership validated (Default data used or received).");
            }
            return ResponseEntity.status(400).body("Rejected: Invalid referral or payment.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }
}