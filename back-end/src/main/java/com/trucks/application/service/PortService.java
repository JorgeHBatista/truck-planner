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

import com.trucks.application.port.PortRepository;
import com.trucks.domain.entity.Port;

public class PortService {

    @Autowired
    PortRepository portRepository;

    public Page<Port> findAll(PageRequest pageRequest) {
        return this.portRepository.findAll(pageRequest);
    }

    public Port save(Port newPort) {
        return this.portRepository.save(newPort);
    }

    public Optional<Port> find(UUID id) {
        return this.portRepository.find(id);
    }

    public Port delete(UUID id) {
        return this.portRepository.delete(id);
    }

    public Port update(UUID id, Port updatedPort) {
        return this.portRepository.update(id, updatedPort);
    }

    public List<Port> deleteAll() {
        return this.portRepository.deleteAll();
    }
}