package com.trucks.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.domain.entity.Scale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ScaleRepository {

    Scale save(Scale scale);

    Optional<Scale> find(UUID id);

    Page<Scale> findAll(PageRequest page);

    Scale update(UUID id, Scale scale);

    List<Scale> deleteAll();
}