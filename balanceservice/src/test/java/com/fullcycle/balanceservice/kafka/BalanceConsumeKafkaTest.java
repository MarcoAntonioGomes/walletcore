package com.fullcycle.balanceservice.kafka;

import com.fullcycle.balanceservice.dto.BalanceInputDTO;
import com.fullcycle.balanceservice.entity.Balance;
import com.fullcycle.balanceservice.usecase.SaveBalanceUseCase;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BalanceConsumeKafkaTest {

    @InjectMocks
    private BalanceConsumeKafka balanceConsumeKafka;

    @Mock
    private SaveBalanceUseCase saveBalanceUseCase;

    @Test
    public void mustGenerateBalanceFromPayload() throws JSONException {

        String payload = "{\n" +
                "  \"Name\": \"BalanceUpdated\",\n" +
                "  \"Payload\": {\n" +
                "    \"account_id_from\": \"2de777b6-cff0-4612-b2cc-1e061ef2764b\",\n" +
                "    \"account_id_to\": \"31fa14d4-cef5-4e64-a9a6-698a6a506148\",\n" +
                "    \"balance_account_id_from\": 48,\n" +
                "    \"balance_account_id_to\": 952\n" +
                "  }\n" +
                "}";
      BalanceInputDTO input = balanceConsumeKafka.consume(payload);


        Assert.assertTrue(input.getAccountId().equals("31fa14d4-cef5-4e64-a9a6-698a6a506148"));
        Assert.assertTrue(input.getBalance().doubleValue() == 952.0);

    }
}
