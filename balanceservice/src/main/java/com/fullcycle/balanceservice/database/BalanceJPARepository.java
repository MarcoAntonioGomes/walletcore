package com.fullcycle.balanceservice.database;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface BalanceJPARepository extends JpaRepository<Balance, String> {

    List<Balance> findByAccountId(String accountId);
}
