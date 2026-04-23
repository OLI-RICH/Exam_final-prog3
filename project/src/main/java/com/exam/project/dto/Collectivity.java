package com.exam.project.dto;

import com.exam.project.model.Member;
import java.util.List;

public class Collectivity {
    private String id;
    private String location;
    private String name;
    private List<Member> members;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
}