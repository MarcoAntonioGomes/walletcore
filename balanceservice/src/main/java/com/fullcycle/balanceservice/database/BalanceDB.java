package com.fullcycle.balanceservice.database;
import com.fullcycle.balanceservice.entity.Balance;


import java.util.List;


public interface BalanceDB {

    Balance save(Balance balance);
    List<Balance> findByAccountId(String accountId);

}
