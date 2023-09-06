package com.fullcycle.balanceservice.dto;



import java.math.BigDecimal;


public class BalanceInputDTO {

    private String accountId;
    private BigDecimal balance;

    public BalanceInputDTO(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
