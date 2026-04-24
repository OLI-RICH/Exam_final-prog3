package com.exam.project.model;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Contribution {
    private String id;
    private String memberId;
    private String collectivityId;
    private BigDecimal amount;
    private LocalDate date;
    private String paymentMethod;
    private String description;
}