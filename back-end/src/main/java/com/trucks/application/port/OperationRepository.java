package com.trucks.application.port;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.trucks.domain.entity.Operation;

public interface OperationRepository {

    Operation save(Operation operation);

    Optional<Operation> find(UUID id);

    Page<Operation> findAll(PageRequest pageRequest);

    Operation update(UUID id, Operation operation);

    Operation delete(UUID id);

    List<Operation> deleteAll();
}
