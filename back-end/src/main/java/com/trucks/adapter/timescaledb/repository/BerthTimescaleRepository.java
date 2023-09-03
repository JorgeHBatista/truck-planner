package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import com.trucks.adapter.timescaledb.mapper.BerthRowMapper;
import com.trucks.application.port.BerthRepository;
import com.trucks.domain.entity.Berth;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Scale;
import com.trucks.domain.entity.Port;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BerthTimescaleRepository implements BerthRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BerthRowMapper berthRowMapper = new BerthRowMapper();

    @Override
    public Berth save(Berth berth) {
        String sql = "INSERT INTO vessel_berth_in_port_with_scale (id, id_port, id_vessel) VALUES (?, ?, ?)";
        Object[] params = {
            berth.getId().toString(),
            berth.getPortID().toString(),
            berth.getVesselID().toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Berth inserted succesfully");
        } else {
            System.out.println("Berth not inserted");
        }
        return berth;
    }
 
    @Override
    public Optional<Berth> find(UUID id) {
        String sql = "SELECT * FROM vessel_berth_in_port_with_scale WHERE id=?";
        Object[] params = { id.toString() };
        Berth berth = jdbcTemplate.queryForObject(sql, berthRowMapper, params);
        if (berth != null) {
            System.out.println("Berth finded successfully.");
        } else {
            System.out.println("No berth has been founded.");
        }
        return Optional.ofNullable(berth);
    }

    @Override
    public Page<Berth> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM vessel_berth_in_port_with_scale";
        List<Berth> berths = jdbcTemplate.query(sql, berthRowMapper);
        return new PageImpl<>(berths, pageRequest, berths.size());
    }

    @Override
    public Berth update(UUID id, Berth berth) {
        String sql = "UPDATE vessel_berth_in_port_with_scale SET id_port=?, id_vessel=? WHERE id=?";
        Object[] params = {
            berth.getPortID().toString(),
            berth.getVesselID().toString(),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Update successfully.");
        } else {
            System.out.println("No berth has been updated.");
        }
        return berth;
    }

    @Override
    public Berth delete(UUID id) {
        Optional<Berth> opBerth = find(id);
        if (opBerth.isPresent()) {
            String sql = "DELETE FROM vessel_berth_in_port_with_scale WHERE id=?";
            Object[] params = { id.toString() };
            int result = jdbcTemplate.update(sql, params);
            if (result > 0) {
                System.out.println("Deleted successfully.");
            } else {
                System.out.println("No berth has been deleted.");
            }
            return opBerth.get();
        }
        return null;
    }

    @Override
    public List<Berth> deleteAll() {
        String sql = "SELECT * FROM vessel_berth_in_port_with_scale";
        List<Berth> berths = jdbcTemplate.query(sql, berthRowMapper);
        sql = "DELETE FROM vessel_berth_in_port_with_scale";
        jdbcTemplate.update(sql);
        return berths;
    }
}
