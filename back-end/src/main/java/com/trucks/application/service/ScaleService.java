package com.trucks.application.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.trucks.application.port.ScaleRepository;
import com.trucks.domain.entity.Scale;

public class ScaleService {

    @Autowired
    ScaleRepository scaleRepository;

    public Page<Scale> findAll(PageRequest page) {
        return this.scaleRepository.findAll(page);
    }

    public Optional<Scale> find(UUID id) {
        return this.scaleRepository.find(id);
    }

    public Scale save(Scale newScale) {
        return this.scaleRepository.save(newScale);
    }

    public List<Scale> deleteAll() {
        return this.scaleRepository.deleteAll();
    }
}
