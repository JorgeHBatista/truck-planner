package com.trucks.application.port;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.trucks.domain.entity.Port;

public interface PortRepository {

    Port save(Port port);

    Optional<Port> find(UUID id);

    Page<Port> findAll(PageRequest pageRequest);

    Port update(UUID id, Port port);
    
    Port delete(UUID id);

    List<Port> deleteAll();
}

