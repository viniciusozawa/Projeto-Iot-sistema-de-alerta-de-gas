package com.japola.gas.alert.controller;

import com.japola.gas.alert.entity.GasEntity;
import com.japola.gas.alert.service.GasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gas")
public class GasController {

    private final GasService gasService;

    @GetMapping("/leituras")
    public ResponseEntity<List<GasEntity>> buscarTodas() {
        log.info("Buscando todas as leituras");
        return ResponseEntity.ok(gasService.buscarTodas());
    }

    @GetMapping("/leituras/ultimas")
    public ResponseEntity<List<GasEntity>> buscarUltimas() {
        log.info("Buscando ultimas 10 leituras");
        return ResponseEntity.ok(gasService.buscarUltimasLeituras());
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<GasEntity>> buscarAlertas() {
        log.info("Buscando alertas");
        return ResponseEntity.ok(gasService.buscarAlertas());
    }
}
