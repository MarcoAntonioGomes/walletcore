package com.fullcycle.balanceservice.usecase;


import com.fullcycle.balanceservice.database.BalanceDB;
import com.fullcycle.balanceservice.dto.BalanceOutputDTO;
import com.fullcycle.balanceservice.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindBalanceByAccountIdUseCase {

    @Autowired
    private BalanceDB balanceDB;

    public List<BalanceOutputDTO> execute(String accountId) {
        List<Balance> balanceList = balanceDB.findByAccountId(accountId);
        return balanceList.stream().map(balance -> new BalanceOutputDTO(balance.getAccountId(), balance.getBalance(), balance.getCreatedAt())).collect(Collectors.toList());
    }


}
