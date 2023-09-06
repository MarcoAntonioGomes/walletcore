package com.fullcycle.balanceservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceOutputDTO {

    private String accountId;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public BalanceOutputDTO(String accountId, BigDecimal balance, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
