package com.exam.project.model;
import lombok.*;
import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Account {
    private String id;
    private String type;
    private String ownerId;
    private BigDecimal balance;
    private String holderName;
}