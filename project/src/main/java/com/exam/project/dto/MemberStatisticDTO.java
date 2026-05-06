package com.exam.project.dto;

import java.math.BigDecimal;

public class MemberStatisticDTO {
    private String memberId;
    private String firstName;
    private String lastName;
    private BigDecimal totalPaid;
    private BigDecimal potentialUnpaid;
    private double attendanceRate;

    public MemberStatisticDTO(String memberId, String firstName, String lastName,
                              BigDecimal totalPaid, BigDecimal potentialUnpaid, double attendanceRate) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPaid = totalPaid;
        this.potentialUnpaid = potentialUnpaid;
        this.attendanceRate = attendanceRate;
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

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getPotentialUnpaid() {
        return potentialUnpaid;
    }

    public double getAttendanceRate() {
        return attendanceRate;
    }
}