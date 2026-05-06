package com.exam.project.dto;

public class AttendeeDTO {
    private String memberId;
    private String firstName;
    private String lastName;
    private String status;

    public AttendeeDTO(String memberId, String firstName, String lastName, String status) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }
}
