package com.exam.project.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String collectivityId;
    private LocalDate joiningDate;
    private MemberOccupation occupation;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public long getSeniorityInMonths() {
        return ChronoUnit.MONTHS.between(joiningDate, LocalDate.now());
    }
}
