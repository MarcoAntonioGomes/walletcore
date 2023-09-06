package com.fullcycle.balanceservice.database;
import com.fullcycle.balanceservice.entity.Balance;

public class BalanceFactory {


    public static final Balance buildEntity(com.fullcycle.balanceservice.database.Balance balance) {
        return new Balance(balance.getId(), balance.getAccountId(), balance.getBalance(), balance.getCreatedAt());
    }

    public static final com.fullcycle.balanceservice.database.Balance buildObjectDB(Balance balance)    {
        return new com.fullcycle.balanceservice.database.Balance(balance.getId(), balance.getAccountId(), balance.getBalance());
    }

}
