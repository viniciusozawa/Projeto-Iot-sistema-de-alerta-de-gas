package com.japola.gas.alert.service;

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqttService {
    private final Mqtt5AsyncClient client;
    private final GasService gasService;

    @Value("${mqtt.topic.leitura}")
    private String topicLeitura;

    @Value("${mqtt.topic.alerta}")
    private String topicAlerta;

    @PostConstruct
    public void subscribe() {
        client.subscribeWith()
                .topicFilter(topicLeitura)
                .callback(this::processarMensagem)
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        log.error("Erro ao subscribe leitura: {}", throwable.getMessage());
                    }else{
                        log.info("Sucesso ao subscribe leitura: {}", topicLeitura);
                    }
                });

        client.subscribeWith()
                .topicFilter(topicAlerta)
                .callback(this::processarMensagem)
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        log.error("Erro ao subscribe alerta: {}", throwable.getMessage());
                    }else{
                        log.info("Sucesso ao subscribe alerta: {}", topicLeitura);
                    }
                });
    }

    private void processarMensagem(Mqtt5Publish publish) {
        String topico = publish.getTopic().toString();
        String payload = StandardCharsets.UTF_8.decode(
                publish.getPayload().get()
        ).toString();

        log.info("Mensagem recebida: [{}]: {}",topico, payload);
        gasService.processarLeitura(payload);
    }

}
