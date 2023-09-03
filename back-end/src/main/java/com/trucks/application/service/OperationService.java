package com.trucks.application.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.trucks.application.port.OperationRepository;
import com.trucks.domain.entity.Operation;

public class OperationService {

    @Autowired
    OperationRepository operationRepository;

    public Page<Operation> findAll(PageRequest pageRequest) {
        return this.operationRepository.findAll(pageRequest);
    }

    public Operation save(Operation newOperation) {
        return this.operationRepository.save(newOperation);
    }

    public Optional<Operation> find(UUID id) {
        return this.operationRepository.find(id);
    }

    public Operation update(UUID id, Operation newOperation) {
        return this.operationRepository.update(id, newOperation);
    }

    public Operation delete(UUID id) {
        return this.operationRepository.delete(id);
    }

    public List<Operation> deleteAll() {
        return this.operationRepository.deleteAll();
    }
}