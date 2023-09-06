package com.fullcycle.balanceservice.web;

import com.fullcycle.balanceservice.usecase.FindBalanceByAccountIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/balances")
public class BalanceController {

    @Autowired
    private FindBalanceByAccountIdUseCase findBalanceByAccountIdUseCase;

    @GetMapping(value = "/{account_id}")
    public ResponseEntity<?> getJoindersInSomeJoinderFridge(@PathVariable String account_id ){
        return ResponseEntity.ok().body(findBalanceByAccountIdUseCase.execute(account_id));
    }

}
