package com.exam.project.model;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Collectivity {
    private String id;
    private String name;
    private String city;
    private LocalDate creationDate;
    private String identificationNumber;
    private String uniqueName;
    private List<Member> members;
}