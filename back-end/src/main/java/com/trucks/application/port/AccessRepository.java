package com.trucks.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.domain.entity.Access;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AccessRepository {

    Access save(Access access);

    Page<Access> findAll(PageRequest page);

    Access update(UUID id, Access access);

    List<Access> deleteAll();
}
