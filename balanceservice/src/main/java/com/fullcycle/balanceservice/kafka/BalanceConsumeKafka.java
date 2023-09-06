package com.fullcycle.balanceservice.kafka;

import com.fullcycle.balanceservice.dto.BalanceInputDTO;
import com.fullcycle.balanceservice.usecase.SaveBalanceUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BalanceConsumeKafka {

    @Autowired
    private SaveBalanceUseCase saveBalanceUseCase;

    public BalanceInputDTO consume(String valor) throws JSONException {
        JSONObject json = new JSONObject(valor);
        JSONObject jsonPayload =  json.getJSONObject("Payload");
        BalanceInputDTO input  = new BalanceInputDTO((String) jsonPayload.get("account_id_to"), BigDecimal.valueOf(jsonPayload.getDouble("balance_account_id_to")));
        saveBalanceUseCase.execute(input);
        return input;
    }


}
