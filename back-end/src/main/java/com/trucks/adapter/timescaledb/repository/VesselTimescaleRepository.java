package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import com.trucks.application.port.VesselRepository;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Scale;
import com.trucks.domain.entity.Operation;
import com.trucks.adapter.timescaledb.mapper.VesselRowMapper;
import com.trucks.adapter.timescaledb.mapper.ScaleRowMapper;
import com.trucks.adapter.timescaledb.mapper.OperationRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class VesselTimescaleRepository implements VesselRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    VesselRowMapper vesselRowMapper = new VesselRowMapper();
    ScaleRowMapper scaleRowMapper = new ScaleRowMapper();
    OperationRowMapper operationRowMapper = new OperationRowMapper();

    @Override
    public Vessel save(Vessel vessel) {
        String sql = "INSERT INTO vessel (id) VALUES (?)";
        Object[] params = {
            vessel.getId().toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Vessel inserted succesfully");
        } else {
            System.out.println("Vessel not inserted");
        }
        List<Scale> scales = vessel.getScales();
        int counter = 0;
        sql = "INSERT INTO vessel_do_scale (id_vessel, id_scale) VALUES (?, ?)";
        for (Scale scale : scales) {
            Object[] paramsScale = {
                vessel.getId().toString(),
                scale.getId().toString()
            };
            if (jdbcTemplate.update(sql, paramsScale) == 1) {
                System.out.println("Vessel do scale #" + counter + " inserted succesfully");
            } else {
                System.out.println("Vessel do scale #" + counter + " not inserted");
            }
            counter++;
        }
        return vessel;
    }

    @Override
    public Optional<Vessel> find(UUID id) {
        String sql = "SELECT * FROM vessel WHERE id=?";
        Object[] params = { id.toString() };
        Vessel vessel = jdbcTemplate.queryForObject(sql, vesselRowMapper, params);
        if (vessel != null) {
            List<Scale> scales = new ArrayList<>();
            sql = "SELECT id_scale FROM vessel_do_scale WHERE id_vessel=?";
            List<String> scalesString = jdbcTemplate.queryForList(
                sql, String.class, params);
            for (String scaleId : scalesString) {
                Optional<Scale> opScale = this.getScale(UUID.fromString(scaleId));
                if (opScale.isPresent()) {
                    scales.add(opScale.get());
                }
            }
            vessel.setScales(scales);
            System.out.println("Vessel finded successfully.");
        } else {
            System.out.println("No vessel has been founded.");
        }
        return Optional.ofNullable(vessel);
    }

    @Override
    public Page<Vessel> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM vessel";
        List<Vessel> vessels = jdbcTemplate.query(sql, vesselRowMapper);

        for (Vessel vessel : vessels) {
            List<Scale> scales = new ArrayList<>();
            sql = "SELECT id_scale FROM vessel_do_scale WHERE id_vessel=?";
            Object[] params = { vessel.getId().toString() };
            List<String> scalesString = jdbcTemplate.queryForList(
                sql, String.class, params);
            for (String scaleId : scalesString) {
                Optional<Scale> opScale = this.getScale(UUID.fromString(scaleId));
                if (opScale.isPresent()) {
                    scales.add(opScale.get());
                }
            }
            vessel.setScales(scales);
        }

        return new PageImpl<>(vessels, pageRequest, vessels.size());
    }

    public Optional<Scale> getScale(UUID id) {

        String sql = "SELECT * FROM scale WHERE id=?";
        Object[] params = { id.toString() };
        Scale scale = jdbcTemplate.queryForObject(sql, scaleRowMapper, params);

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

    public Optional<Operation> getOperation(UUID id) {
        String sql = "SELECT * FROM operation WHERE id=?";
        Object[] params = { id.toString() };
        Operation operation = jdbcTemplate.queryForObject(sql, operationRowMapper, params);
        return Optional.ofNullable(operation);
    }

    @Override
    public Vessel update(UUID id, Vessel vessel) {
        String sql = "UPDATE vessel SET id=? WHERE id=?";
        Object[] params = {
            vessel.getId().toString(),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            List<Scale> scales = vessel.getScales();
            sql = "UPDATE vessel_do_scale SET id_scale=? WHERE id_vessel=?";
            for (Scale scale : scales) {
                Object[] paramsScale = {
                    scale.getId().toString(),
                    vessel.getId().toString()
                };
                if (jdbcTemplate.update(sql, paramsScale) == 1) {
                    System.out.println("Update successfully.");
                } else {
                    System.out.println("Not able to update the scales from vessel");
                }
            }
        } else {
            System.out.println("No vessel has been updated.");
        }
        return vessel;
    }

    @Override
    public Vessel delete(UUID id) {
        Optional<Vessel> opVessel = find(id);
        if (opVessel.isPresent()) {
            String sql = "DELETE FROM vessel WHERE id=?";
            Object[] params = { id.toString() };
            int result = jdbcTemplate.update(sql, params);
            if (result > 0) {
                sql = "DELETE FROM vessel_berth_in_port_with_scale where id_vessel=?";
                result = jdbcTemplate.update(sql, params);
                if (result > 0) {
                    sql = "DELETE FROM vessel_do_scale where id_vessel=?";
                    result = jdbcTemplate.update(sql, params);
                    if (result > 0) {
                        System.out.println("Deleted successfully.");
                    }
                    else {
                        System.out.println("No vessel do scale has been deleted.");
                    }
                } else {
                    System.out.println("No berth has been deleted.");
                }
            } else {
                System.out.println("No vessel has been deleted.");
            }
            return opVessel.get();
        }
        return null;
    }

    @Override
    public List<Vessel> deleteAll() {
        String sql = "SELECT * FROM vessel";
        List<Vessel> vessels = jdbcTemplate.query(sql, vesselRowMapper);
        sql = "DELETE FROM vessel";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM vessel_do_scale";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM vessel_berth_in_port_with_scale";
        jdbcTemplate.update(sql);
        return vessels;
    }
}
