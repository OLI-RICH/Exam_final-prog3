package com.exam.project.model;
import lombok.*;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String collectivityId;
    private LocalDate joinDate;
    private String status;
}