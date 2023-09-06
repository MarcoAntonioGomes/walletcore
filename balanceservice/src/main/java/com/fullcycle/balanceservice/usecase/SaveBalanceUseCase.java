package com.fullcycle.balanceservice.usecase;


import com.fullcycle.balanceservice.database.BalanceDB;
import com.fullcycle.balanceservice.dto.BalanceInputDTO;
import com.fullcycle.balanceservice.dto.BalanceOutputDTO;
import com.fullcycle.balanceservice.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveBalanceUseCase {

    @Autowired
    @Qualifier("balanceDBImpl")
    private BalanceDB balanceDB;

    public BalanceOutputDTO execute(BalanceInputDTO balanceInputDTO) {

        Balance balance = new Balance(UUID.randomUUID().toString(), balanceInputDTO.getAccountId(), balanceInputDTO.getBalance());
        Balance balanceSaved = balanceDB.save(balance);
        BalanceOutputDTO balanceOutputDTO = new BalanceOutputDTO(balanceSaved.getAccountId(), balanceSaved.getBalance(), balanceSaved.getCreatedAt());

        return balanceOutputDTO;
    }


}
