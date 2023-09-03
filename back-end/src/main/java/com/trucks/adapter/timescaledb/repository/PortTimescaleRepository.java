package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import com.trucks.application.port.PortRepository;
import com.trucks.domain.entity.Port;
import com.trucks.adapter.timescaledb.mapper.PortRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class PortTimescaleRepository implements PortRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    PortRowMapper portRowMapper = new PortRowMapper();

    @Override
    public Port save(Port port) {
        String sql = "INSERT INTO port (id, name, coordinate) VALUES (?, ?, ?)";
        Object[] params = {
            port.getId().toString(),
            port.getName().getPortName(),
            port.getCoordinate().toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Port inserted succesfully");
        } else {
            System.out.println("Port not inserted");
        }
        return port;
    }

    @Override
    public Optional<Port> find(UUID id) {
        String sql = "SELECT * FROM port WHERE id=?";
        Object[] params = { id.toString() };
        Port port = jdbcTemplate.queryForObject(sql, portRowMapper, params);
        if (port != null) {
            System.out.println("Port finded successfully.");
        } else {
            System.out.println("No port has been founded.");
        }
        return Optional.ofNullable(port);
    }

    @Override
    public Page<Port> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM port";
        List<Port> ports = jdbcTemplate.query(sql, portRowMapper);
        return new PageImpl<>(ports, pageRequest, ports.size());
    }

    @Override
    public Port update(UUID id, Port port) {
        String sql = "UPDATE port SET id=?, name=?, coordinate=?  WHERE id=?";
        Object[] params = {
            port.getId().toString(),
            port.getName().getPortName(),
            port.getCoordinate().toString(),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Update successfully.");
        } else {
            System.out.println("No port has been updated.");
        }
        return port;
    }

    @Override
    public Port delete(UUID id) {
        Optional<Port> opPort = find(id);
        if (opPort.isPresent()) {
            String sql = "DELETE FROM port WHERE id=?";
            Object[] params = { id.toString() };
            int result = jdbcTemplate.update(sql, params);
            if (result > 0) {
                sql = "DELETE FROM vessel_berth_in_port_with_scale WHERE id_port=?";
                result = jdbcTemplate.update(sql, params);
                if (result > 0) {
                    sql = "DELETE FROM truck_access_port WHERE id_port=?";
                    result = jdbcTemplate.update(sql, params);
                    if (result > 0) {
                        System.out.println("Deleted successfully.");
                    }
                    else {
                        System.out.println("No access has been deleted.");
                    }
                } else {
                    System.out.println("No berth has been deleted.");
                }
            } else {
                System.out.println("No port has been deleted.");
            }
            return opPort.get();
        }
        return null;
    }

    @Override
    public List<Port> deleteAll() {
        String sql = "SELECT * FROM port";
        List<Port> ports = jdbcTemplate.query(sql, portRowMapper);
        sql = "DELETE FROM port";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM vessel_berth_in_port_with_scale";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM truck_access_port";
        jdbcTemplate.update(sql);
        return ports;
    }
}
