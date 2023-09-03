package com.trucks.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.domain.entity.Vessel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface VesselRepository {

    Vessel save(Vessel truck);

    Optional<Vessel> find(UUID id);

    Page<Vessel> findAll(PageRequest pageRequest);

    Vessel update(UUID id, Vessel truck);
    
    Vessel delete(UUID id);

    List<Vessel> deleteAll();
}
