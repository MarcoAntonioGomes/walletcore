package com.fullcycle.balanceservice.usecase;


import com.fullcycle.balanceservice.database.BalanceDB;
import com.fullcycle.balanceservice.database.BalanceDBImpl;
import com.fullcycle.balanceservice.dto.BalanceInputDTO;
import com.fullcycle.balanceservice.dto.BalanceOutputDTO;
import com.fullcycle.balanceservice.entity.Balance;

import java.util.UUID;

public class SaveBalanceUseCase {

    private BalanceDB balanceDB = new BalanceDBImpl();

    public BalanceOutputDTO Execute(BalanceInputDTO balanceInputDTO) {

        Balance balance = new Balance(UUID.randomUUID().toString(), balanceInputDTO.getAccountId(), balanceInputDTO.getBalance());
        Balance balanceSaved = balanceDB.save(balance);
        BalanceOutputDTO balanceOutputDTO = new BalanceOutputDTO(balanceSaved.getAccountId(), balanceSaved.getBalance(), balanceSaved.getCreatedAt());

        return balanceOutputDTO;
    }


}
