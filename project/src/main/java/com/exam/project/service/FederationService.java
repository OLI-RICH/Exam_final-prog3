package com.exam.project.service;

import com.exam.project.dto.CreateCollectivity;
import com.exam.project.dto.CreateMember;
import com.exam.project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class FederationService {
    private final MemberRepository memberRepository;

    public FederationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean validateCollectivityA(CreateCollectivity dto) throws SQLException {
        if (dto == null || dto.getMembers() == null) return false;
        return dto.isFederationApproval() &&
                dto.getMembers().size() >= 10 &&
                memberRepository.countSeniors(dto.getMembers()) >= 5;
    }

    public boolean validateNewMemberB2(CreateMember dto) throws SQLException {
        if (dto == null || dto.getReferees() == null) return false;
        return dto.isRegistrationFeePaid() &&
                dto.getReferees().size() >= 2 &&
                memberRepository.countSeniors(dto.getReferees()) >= 2;
    }
}