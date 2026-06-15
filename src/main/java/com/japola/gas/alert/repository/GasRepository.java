package com.japola.gas.alert.repository;

import com.japola.gas.alert.entity.GasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GasRepository extends JpaRepository<GasEntity, UUID > {

    List<GasEntity> findByAlertaTrue();
    List<GasEntity> findTop10ByOrderByDataHoraDesc();

}
