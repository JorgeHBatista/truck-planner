package com.trucks.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.domain.entity.Truck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TruckRepository {

    Truck save(Truck truck);

    Optional<Truck> find(UUID id);
    
    Page<Truck> findAll(PageRequest pageRequest);

    Truck update(UUID id, Truck truck);
    
    Truck delete(UUID id);

    List<Truck> deleteAll();
}
