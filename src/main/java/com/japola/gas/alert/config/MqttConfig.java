package com.japola.gas.alert.config;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${hivemq.host}")
    private String host;

    @Value("${hivemq.port}")
    private int port;

    @Value("${hivemq.username}")
    private String username;

    @Value("${hivemq.password}")
    private String password;

    @Bean
    public Mqtt5AsyncClient mqtt5AsyncClient() {
        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildAsync();

        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(password.getBytes())
                .applySimpleAuth()
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Erro ao conectar no HiveMQ: " + throwable.getMessage());
                    } else {
                        System.out.println("HiveMQ conectado com sucesso!");
                    }
                });

        return client;
    }
}
