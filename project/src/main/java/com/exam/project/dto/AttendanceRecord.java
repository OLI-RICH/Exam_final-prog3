package com.exam.project.dto;

public class AttendanceRecord {
    private String memberId;
    private String status;

    public AttendanceRecord() {
    }

    public AttendanceRecord(String memberId, String status) {
        this.memberId = memberId;
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}