package com.trucks.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.trucks.application.port.BerthRepository;
import com.trucks.domain.entity.Berth;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class BerthService {

    @Autowired
    BerthRepository berthRepository;

    public Page<Berth> findAll(PageRequest pageRequest) {
        return this.berthRepository.findAll(pageRequest);
    }

    public Berth save(Berth newBerth) {
        return this.berthRepository.save(newBerth);
    }

    public Optional<Berth> find(UUID id) {
        return this.berthRepository.find(id);
    }

    public Berth delete(UUID id) {
        return this.berthRepository.delete(id);
    }

    public Berth update(UUID id, Berth updatedBerth) {
        return this.berthRepository.update(id, updatedBerth);
    }
    

    public List<Berth> deleteAll() {
        return this.berthRepository.deleteAll();
    }
}