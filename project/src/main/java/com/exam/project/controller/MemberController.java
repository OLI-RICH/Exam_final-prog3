package com.exam.project.controller;

import com.exam.project.model.Member;
import com.exam.project.model.Contribution;
import com.exam.project.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<?> addMember(@RequestBody Member m) {
        try {
            if (m.getId() == null || m.getId().isEmpty()) {
                m.setId("MEM-" + UUID.randomUUID().toString().substring(0, 5));
            }
            if (m.getJoinDate() == null) {
                m.setJoinDate(LocalDate.now());
            }

            memberService.addMember(m);

            return ResponseEntity.ok(m);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@PathVariable String id) {
        try {
            Member member = memberService.getMemberById(id);
            if (member == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<?> recordPayment(@PathVariable String id, @RequestBody Contribution payment) {
        try {
            if (payment.getId() == null || payment.getId().isEmpty()) {
                payment.setId("PAY-" + UUID.randomUUID().toString().substring(0, 8));
            }
            if (payment.getDate() == null) {
                payment.setDate(LocalDate.now());
            }

            memberService.recordPayment(id, payment);

            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}