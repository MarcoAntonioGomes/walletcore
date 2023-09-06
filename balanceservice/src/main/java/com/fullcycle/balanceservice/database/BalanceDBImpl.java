package com.fullcycle.balanceservice.database;

import com.fullcycle.balanceservice.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;



import java.util.List;
import java.util.stream.Collectors;

@Component("balanceDBImpl")
@Primary
public class BalanceDBImpl implements BalanceDB{

    @Autowired
    private  BalanceJPARepository balanceJPARepository;



    @Override
    public Balance save(Balance balance) {
        return BalanceFactory.buildEntity(balanceJPARepository.save(BalanceFactory.buildObjectDB(balance)));
    }

    @Override
    public List<Balance> findByAccountId(String accountId) {
        List<com.fullcycle.balanceservice.database.Balance> balances = balanceJPARepository.findByAccountId(accountId);
        return  balances.stream().map(balance ->  new Balance(balance.getId(), balance.getAccountId(), balance.getBalance(), balance.getCreatedAt())).collect(Collectors.toList());
    }
}
