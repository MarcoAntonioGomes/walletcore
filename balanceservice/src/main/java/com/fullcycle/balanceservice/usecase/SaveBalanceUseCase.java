package com.fullcycle.balanceservice.usecase;


import com.fullcycle.balanceservice.database.BalanceDB;
import com.fullcycle.balanceservice.database.BalanceDBImpl;
import com.fullcycle.balanceservice.dto.BalanceInputDTO;
import com.fullcycle.balanceservice.dto.BalanceOutputDTO;
import com.fullcycle.balanceservice.entity.Balance;

public class SalvarBalanceUseCase {

    private BalanceDB balanceDB = new BalanceDBImpl();

    public BalanceOutputDTO Execute(BalanceInputDTO balanceInputDTO) {

        Balance balance = new Balance(balanceInputDTO.getAccountId(), balanceInputDTO.getBalance());

        Balance balance = balanceDB.save()
        Balance balanceSaved = balanceDB.save(balance);
        return BalanceFactory.buildDTO(balanceSaved);
    }


}
