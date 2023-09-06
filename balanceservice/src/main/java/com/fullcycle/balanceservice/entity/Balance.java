package com.fullcycle.balanceservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Balance {

    private String id;
    private String accountId;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public Balance(String id, String accountId, BigDecimal balance) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
    }

    public Balance(String id, String accountId, BigDecimal balance, LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Balance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
