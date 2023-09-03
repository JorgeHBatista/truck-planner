package com.trucks.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.trucks.application.port.AccessRepository;
import com.trucks.domain.entity.Access;

public class AccessService {

    @Autowired
    AccessRepository accessRepository;

    public Page<Access> findAll(PageRequest page) {
        return this.accessRepository.findAll(page);
    }

    public Access save(Access newAccess) {
        return this.accessRepository.save(newAccess);
    }

    public List<Access> deleteAll() {
        return this.accessRepository.deleteAll();
    }
}
