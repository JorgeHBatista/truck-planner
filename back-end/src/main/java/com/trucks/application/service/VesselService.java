package com.trucks.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.trucks.application.port.VesselRepository;
import com.trucks.domain.entity.Vessel;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class VesselService {

    @Autowired
    VesselRepository vesselRepository;

    public Page<Vessel> findAll(PageRequest pageRequest) {
        return this.vesselRepository.findAll(pageRequest);
    }

    public Vessel save(Vessel newVessel) {
        return this.vesselRepository.save(newVessel);
    }

    public Optional<Vessel> find(UUID id) {
        return this.vesselRepository.find(id);
    }

    public Vessel delete(UUID id) {
        return this.vesselRepository.delete(id);
    }

    public Vessel update(UUID id, Vessel updatedVessel) {
        return this.vesselRepository.update(id, updatedVessel);
    }
    

    public List<Vessel> deleteAll() {
        return this.vesselRepository.deleteAll();
    }
}