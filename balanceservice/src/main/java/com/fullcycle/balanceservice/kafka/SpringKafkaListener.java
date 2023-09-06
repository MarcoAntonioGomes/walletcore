package com.fullcycle.balanceservice.kafka;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SpringKafkaListener {

    @Autowired
    private BalanceConsumeKafka balanceConsumeKafka;

    @KafkaListener(topics = "balances", groupId = "wallet")
    public void consume(@Payload String valor, Acknowledgment ack) throws JSONException {

        balanceConsumeKafka.consume(valor);

        // Commmit manual, que também será síncrono
        ack.acknowledge();

    }
}