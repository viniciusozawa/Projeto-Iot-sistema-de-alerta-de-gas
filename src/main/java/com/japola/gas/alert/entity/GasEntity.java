package com.japola.gas.alert.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "gas")
public class GasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer valor;

    @Column(nullable = false)
    private Integer percentual;

    @Column(nullable = false)
    private boolean alerta;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;




}
