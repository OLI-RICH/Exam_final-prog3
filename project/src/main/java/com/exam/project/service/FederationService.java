package com.exam.project.service;

import com.exam.project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class FederationService {
    private final MemberRepository memberRepository;

    public FederationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean validateCollectivityA(Map<String, Object> data) throws SQLException {
        Boolean approved = (Boolean) data.get("federationApproval");
        List<String> members = (List<String>) data.get("members");
        return Boolean.TRUE.equals(approved) && members != null &&
                members.size() >= 10 && memberRepository.countSeniors(members) >= 5;
    }

    public boolean validateNewMemberB2(Map<String, Object> data) throws SQLException {
        List<String> godparents = (List<String>) data.get("godparents");
        Object paymentObj = data.get("payment");

        if (godparents == null || godparents.size() != 2 || paymentObj == null) {
            return false;
        }

        double paymentValue = Double.parseDouble(paymentObj.toString());

        return paymentValue >= 50000.0 && memberRepository.countSeniors(godparents) >= 2;
    }
}