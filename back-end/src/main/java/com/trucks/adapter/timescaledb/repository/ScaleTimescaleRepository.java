package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.sql.Timestamp;

import com.trucks.utils.TruckUtils;

import java.util.ArrayList;

import com.trucks.application.port.ScaleRepository;
import com.trucks.domain.entity.Scale;
import com.trucks.domain.entity.Operation;
import com.trucks.adapter.timescaledb.mapper.ScaleRowMapper;
import com.trucks.adapter.timescaledb.mapper.OperationRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ScaleTimescaleRepository implements ScaleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    ScaleRowMapper scaleRowMapper = new ScaleRowMapper();
    OperationRowMapper operationRowMapper = new OperationRowMapper();

    @Override
    public Scale save(Scale scale) {
        String sql = "INSERT INTO scale (id, starting_time, finishing_time) VALUES (?, ?, ?)";
        Object[] params = {
            scale.getId().toString(),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(scale.getStartingTime())),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(scale.getFinishingTime()))
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Scale inserted succesfully");
        } else {
            System.out.println("Scale not inserted");
        }
        List<Operation> operations = scale.getOperations();
        int counter = 0;
        sql = "INSERT INTO scale_do_operation (id_scale, id_operation) VALUES (?, ?)";
        for (Operation operation : operations) {
            Object[] paramsOperation = {
                scale.getId().toString(),
                operation.getId().toString()
            };
            if (jdbcTemplate.update(sql, paramsOperation) == 1) {
                System.out.println("Scale do operation #" + counter + " inserted succesfully");
            } else {
                System.out.println("Scale do operation #" + counter + " not inserted");
            }
            counter++;
        }
        return scale;
    }

    @Override
    public Optional<Scale> find(UUID id) {
        String sql = "SELECT * FROM scale WHERE id=?";
        Object[] params = { id.toString() };
        Scale scale = jdbcTemplate.queryForObject(sql, scaleRowMapper, params);
        if (scale == null) {
            return Optional.empty();
        }
        List<Operation> operations = new ArrayList<>();
        sql = "SELECT id_operation FROM scale_do_operation WHERE id_scale=?";
        List<String> operationsString = jdbcTemplate.queryForList(
            sql, String.class, params);
        for (String operationId : operationsString) {
            Optional<Operation> opOperation = this.getOperation(UUID.fromString(operationId));
            if (opOperation.isPresent()) {
                operations.add(opOperation.get());
            }
        }
        scale.setOperations(operations);
        return Optional.ofNullable(scale);
    }


    @Override
    public Page<Scale> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM scale";
        List<Scale> scales = jdbcTemplate.query(sql, scaleRowMapper);

        for (Scale scale : scales) {
            List<Operation> operations = new ArrayList<>();
            sql = "SELECT id_operation FROM scale_do_operation WHERE id_scale=?";
            Object[] params = { scale.getId().toString() };
            List<String> operationsString = jdbcTemplate.queryForList(
                sql, String.class, params);
            for (String operationId : operationsString) {
                Optional<Operation> opOperation = this.getOperation(UUID.fromString(operationId));
                if (opOperation.isPresent()) {
                    operations.add(opOperation.get());
                }
            }
            scale.setOperations(operations);
        }
        return new PageImpl<>(scales, pageRequest, scales.size());
    }

    public Optional<Operation> getOperation(UUID id) {
        String sql = "SELECT * FROM operation WHERE id=?";
        Object[] params = { id.toString() };
        Operation operation = jdbcTemplate.queryForObject(sql, operationRowMapper, params);
        return Optional.ofNullable(operation);
    }

    @Override
    public Scale update(UUID id, Scale scale) {
        String sql = "UPDATE scale SET id=?, starting_time=?, finishing_time=?  WHERE id=?";
        Object[] params = {
            scale.getId().toString(),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(scale.getStartingTime())),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(scale.getFinishingTime())),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Update successfully.");
        } else {
            System.out.println("No scale has been updated.");
        }
        return scale;
    }

    @Override
    public List<Scale> deleteAll() {
        String sql = "SELECT * FROM scale";
        List<Scale> scales = jdbcTemplate.query(sql, scaleRowMapper);
        sql = "DELETE FROM scale";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM scale_do_operation";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM vessel_do_scale";
        jdbcTemplate.update(sql);
        return scales;
    }
}