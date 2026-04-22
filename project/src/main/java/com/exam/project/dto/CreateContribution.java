package com.exam.project.dto;

import com.exam.project.model.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateContribution {
    private String memberId;
    private String collectivityId;
    private BigDecimal amount;
    private LocalDate date;
    private PaymentMethod paymentMethod;
    private String description;

    // Getters and setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}