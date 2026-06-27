package com.japola.gas.alert.service;

import com.japola.gas.alert.entity.GasEntity;
import com.japola.gas.alert.repository.GasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GasService {


    private final GasRepository gasRepository;
    private final ObjectMapper objectMapper;


    public void processarLeitura(String payload) {
        try {
            var dados = objectMapper.readValue(payload, java.util.Map.class);

            int valor      = (int) dados.get("valor");
            int percentual = (int) dados.get("percentual");
            boolean alerta = (boolean) dados.get("alerta");

            GasEntity leitura = GasEntity.builder()
                    .valor(valor)
                    .percentual(percentual)
                    .alerta(alerta)
                    .dataHora(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                    .build();

            gasRepository.save(leitura);

            // Dispara alerta automaticamente se detectar gás
            if (alerta) {
                log.warn("GAS DETECTADO! Disparando alertas...");

            } else {
                log.info("Leitura normal — valor: {} ({}%)", valor, percentual);
            }

        } catch (Exception e) {
            log.error("Erro ao processar mensagem MQTT: {}", e.getMessage());
        }
    }

    public List<GasEntity> buscarTodas() {
        return gasRepository.findAll();
    }

    public List<GasEntity> buscarAlertas() {
        return gasRepository.findByAlertaTrue();
    }

    public List<GasEntity> buscarUltimasLeituras() {
        return gasRepository.findTop10ByOrderByDataHoraDesc();
    }
}
