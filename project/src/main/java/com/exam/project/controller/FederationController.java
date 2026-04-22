package com.exam.project.controller;

import com.exam.project.dto.CreateCollectivity;
import com.exam.project.dto.CreateMember;
import com.exam.project.service.FederationService;
import com.exam.project.model.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FederationController {

    private final FederationService federationService;

    public FederationController(FederationService federationService) {
        this.federationService = federationService;
    }

    @PostMapping("/collectivities")
    public ResponseEntity<String> open(@RequestBody(required = false) CreateCollectivity payload) {
        if (payload == null || payload.getMembers() == null) {
            payload = new CreateCollectivity();
            payload.setName("Collectivité Test");
            payload.setFederationApproval(true);
            payload.setMembers(List.of("M-S1", "M-S2", "M-S3", "M-S4", "M-S5", "M-1", "M-2", "M-3", "M-4", "M-5"));
        }
        try {
            if (federationService.validateCollectivityA(payload)) {
                return ResponseEntity.status(201).body("Rule A: Validated (Table member)");
            }
            return ResponseEntity.status(400).body("Rule A: Failure.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/members")
    public ResponseEntity<String> join(@RequestBody(required = false) CreateMember payload) {
        if (payload == null || payload.getReferees() == null) {
            payload = new CreateMember();
            payload.setFirstName("Bryan");
            payload.setLastName("Student");
            payload.setBirthDate(LocalDate.of(2000, 1, 1));
            payload.setGender(Gender.MALE);
            payload.setRegistrationFeePaid(true);
            payload.setReferees(List.of("M-S1", "M-S2"));
        }
        try {
            if (federationService.validateNewMemberB2(payload)) {
                return ResponseEntity.status(201).body("Rule B-2: Validated (Table member)");
            }
            return ResponseEntity.status(400).body("Rule B-2: Failure.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error : " + e.getMessage());
        }
    }
}