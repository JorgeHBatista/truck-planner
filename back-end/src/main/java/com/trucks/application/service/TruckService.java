package com.trucks.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.trucks.application.port.TruckRepository;
import com.trucks.domain.entity.Truck;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class TruckService {

    @Autowired
    TruckRepository truckRepository;

    public Page<Truck> findAll(PageRequest pageRequest) {
        return this.truckRepository.findAll(pageRequest);
    }

    public Truck save(Truck newTruck) {
        return this.truckRepository.save(newTruck);
    }

    public Optional<Truck> find(UUID id) {
        return this.truckRepository.find(id);
    }

    public Truck delete(UUID id) {
        return this.truckRepository.delete(id);
    }

    public Truck update(UUID id, Truck updatedTruck) {
        return this.truckRepository.update(id, updatedTruck);
    }
    

    public List<Truck> deleteAll() {
        return this.truckRepository.deleteAll();
    }
}