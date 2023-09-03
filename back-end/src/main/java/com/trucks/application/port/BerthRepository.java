package com.trucks.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.domain.entity.Berth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BerthRepository {

    Berth save(Berth berth);

    Optional<Berth> find(UUID id);

    Page<Berth> findAll(PageRequest pageRequest);

    Berth update(UUID id, Berth berth);
    
    Berth delete(UUID id);

    List<Berth> deleteAll();
}
