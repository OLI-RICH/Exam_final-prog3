package com.exam.project.model;

import java.math.BigDecimal;

public class Account {
    private String id;
    private AccountType type;
    private String ownerId; 
    private BigDecimal balance; 

    
    private String holderName;
    private BankName bankName;
    private String accountNumber; 

    
    private MobileMoneyService mobileService;
    private String phoneNumber;

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public BankName getBankName() { return bankName; }
    public void setBankName(BankName bankName) { this.bankName = bankName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public MobileMoneyService getMobileService() { return mobileService; }
    public void setMobileService(MobileMoneyService mobileService) { this.mobileService = mobileService; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}