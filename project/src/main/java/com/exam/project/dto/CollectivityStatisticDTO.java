package com.exam.project.dto;

public class CollectivityStatisticDTO {
    private String collectivityId;
    private String collectivityName;
    private double upToDatePercentage;
    private long newMembersCount;

    public CollectivityStatisticDTO(String collectivityId, String collectivityName, double upToDatePercentage, long newMembersCount) {
        this.collectivityId = collectivityId;
        this.collectivityName = collectivityName;
        this.upToDatePercentage = upToDatePercentage;
        this.newMembersCount = newMembersCount;
    }

    public String getCollectivityId() { return collectivityId; }
    public String getCollectivityName() { return collectivityName; }
    public double getUpToDatePercentage() { return upToDatePercentage; }
    public long getNewMembersCount() { return newMembersCount; }
}