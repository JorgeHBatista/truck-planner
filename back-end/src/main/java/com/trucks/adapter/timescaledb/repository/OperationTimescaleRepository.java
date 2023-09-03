package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.application.port.OperationRepository;
import com.trucks.domain.entity.Operation;
import com.trucks.adapter.timescaledb.mapper.OperationRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class OperationTimescaleRepository implements OperationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    OperationRowMapper operationRowMapper = new OperationRowMapper();

    @Override
    public Operation save(Operation operation) {
        String sql = "INSERT INTO operation (id, cargo, type, quantity) VALUES (?, ?, ?, ?)";
        Object[] params = {
            operation.getId().toString(),
            operation.getCargo().toString(),
            operation.getType().toString(),
            operation.getQuantity().getValue()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Operation inserted succesfully");
        } else {
            System.out.println("Operation not inserted");
        }
        return operation;
    }

    @Override
    public Optional<Operation> find(UUID id) {
        String sql = "SELECT * FROM operation WHERE id=?";
        Object[] params = { id.toString() };
        Operation operation = jdbcTemplate.queryForObject(sql, operationRowMapper, params);
        if (operation != null) {
            System.out.println("Operation finded successfully.");
        } else {
            System.out.println("No operation has been founded.");
        }
        return Optional.ofNullable(operation);
    }

    @Override
    public Page<Operation> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM operation";
        List<Operation> operations = jdbcTemplate.query(sql, operationRowMapper);
        return new PageImpl<>(operations, pageRequest, operations.size());
    }

    @Override 
    public Operation update(UUID id, Operation operation) {
        String sql = "UPDATE operation SET cargo=?, type=?, quantity=? WHERE id=?";
        Object[] params = {
            operation.getCargo().toString(),
            operation.getType().toString(),
            operation.getQuantity().getValue(),
            id.toString()
        };
        int result = jdbcTemplate.update(sql, params);
        if (result > 0) {
            System.out.println("Updated successfully.");
        } else {
            System.out.println("No operation has been updated.");
        }
        return operation;
    }

    @Override
    public Operation delete(UUID id) {
        Optional<Operation> opOperation = find(id);
        if (opOperation.isPresent()) {
            String sql = "DELETE FROM operation WHERE id=?";
            Object[] params = { id.toString() };
            int result = jdbcTemplate.update(sql, params);
            if (result > 0) {
                sql = "DELETE FROM scale_do_operation WHERE id_operation=?";
                result = jdbcTemplate.update(sql, params);
                if (result > 0) {
                    System.out.println("Deleted successfully.");
                } else {
                    System.out.println("No scale do operation has been deleted.");
                }
            } else {
                System.out.println("No operation has been deleted.");
            }
            return opOperation.get();
        }
        return null;
    }

    @Override
    public List<Operation> deleteAll() {
        String sql = "SELECT * FROM operation";
        List<Operation> operations = jdbcTemplate.query(sql, operationRowMapper);
        sql = "DELETE FROM operation";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM scale_do_operation";
        jdbcTemplate.update(sql);
        return operations;
    }
}