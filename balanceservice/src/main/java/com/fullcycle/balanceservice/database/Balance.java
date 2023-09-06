package com.fullcycle.balanceservice.database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "balance")
@Entity
public class Balance {

    @Id
    private String id;
    @Column(name = "account_id")
    private String accountId;
    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


    public Balance(String id, String accountId, BigDecimal balance) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
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
}
