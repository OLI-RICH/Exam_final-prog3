package com.exam.project.controller;

import com.exam.project.dto.AttendanceRecord;
import com.exam.project.dto.AttendeeDTO;
import com.exam.project.dto.CollectivityStatisticDTO;
import com.exam.project.dto.MemberStatisticDTO;
import com.exam.project.model.*;
import com.exam.project.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collectivities")
public class CollectivityController {
    private final CollectivityService collectivityService;
    private final MemberService memberService;
    private final FinancialService financialService;
    private final ContributionService contributionService;
    private final ActivityService activityService;

    public CollectivityController(CollectivityService collectivityService, MemberService memberService,
                                  FinancialService financialService, ContributionService contributionService,
                                  ActivityService activityService) {
        this.collectivityService = collectivityService;
        this.memberService = memberService;
        this.financialService = financialService;
        this.contributionService = contributionService;
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<?> createCollectivity(@RequestBody Collectivity col) {
        try {
            col.setId("COL-" + UUID.randomUUID().toString().substring(0, 5));
            col.setCreationDate(LocalDate.now());
            collectivityService.createCollectivity(col);
            return ResponseEntity.ok(col);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCollectivities() {
        try {
            List<Collectivity> collectivities = collectivityService.getAllCollectivities();
            return ResponseEntity.ok(collectivities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectivity(@PathVariable String id) {
        try {
            Collectivity collectivity = collectivityService.getCollectivityById(id);
            if (collectivity == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(collectivity);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<?> getCollectivityMembers(@PathVariable String id) {
        try {
            List<Member> members = memberService.getMembersByCollectivityId(id);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(@PathVariable String id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        try {
            LocalDate dateQuery = (at != null) ? at : LocalDate.now();
            List<Account> accounts = financialService.getAccountsWithBalance(id, dateQuery);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            List<Contribution> fees = contributionService.getMembershipFeesByCollectivityId(id);
            return ResponseEntity.ok(fees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<Contribution> transactions = contributionService.getTransactionsByCollectivityId(id, start, end);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/identity")
    public ResponseEntity<?> assignIdentity(@PathVariable String id, @RequestParam String number, @RequestParam String name) {
        try {
            collectivityService.assignIdentity(id, number, name);
            return ResponseEntity.ok("Identity successfully assigned");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getCollectivityStatistics(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<MemberStatisticDTO> stats = financialService.getMemberStatistics(id, start, end);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getGlobalStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<CollectivityStatisticDTO> stats = financialService.getGlobalStatistics(start, end);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<?> addActivities(@PathVariable String id, @RequestBody List<Activity> activities) {
        try {
            activityService.addActivities(id, activities);
            return ResponseEntity.ok("Activities successfully added.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<?> getActivities(@PathVariable String id) {
        try {
            List<Activity> activities = activityService.getActivities(id);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> recordAttendance(@PathVariable String id, @PathVariable String activityId, @RequestBody List<AttendanceRecord> records) {
        try {
            activityService.recordAttendance(activityId, records);
            return ResponseEntity.ok("Attendance successfully recorded.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> getAttendance(@PathVariable String id, @PathVariable String activityId) {
        try {
            List<AttendeeDTO> attendees = activityService.getAttendance(activityId);
            return ResponseEntity.ok(attendees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}