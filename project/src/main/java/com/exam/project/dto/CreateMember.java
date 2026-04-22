package com.exam.project.dto;

import java.util.List;

public class CreateMember extends MemberInformation {
    private String collectivityIdentifier;
    private List<String> referees; // MemberIdentifier is String
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;

    // Getters and setters
    public String getCollectivityIdentifier() { return collectivityIdentifier; }
    public void setCollectivityIdentifier(String collectivityIdentifier) { this.collectivityIdentifier = collectivityIdentifier; }

    public List<String> getReferees() { return referees; }
    public void setReferees(List<String> referees) { this.referees = referees; }

    public boolean isRegistrationFeePaid() { return registrationFeePaid; }
    public void setRegistrationFeePaid(boolean registrationFeePaid) { this.registrationFeePaid = registrationFeePaid; }

    public boolean isMembershipDuesPaid() { return membershipDuesPaid; }
    public void setMembershipDuesPaid(boolean membershipDuesPaid) { this.membershipDuesPaid = membershipDuesPaid; }
}