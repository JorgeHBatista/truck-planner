package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trucks.application.port.TruckRepository;
import com.trucks.domain.entity.Truck;
import com.trucks.adapter.timescaledb.mapper.TruckRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TruckTimescaleRepository implements TruckRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    TruckRowMapper truckRowMapper = new TruckRowMapper();

    @Override
    public Truck save(Truck truck) {
        String sql = "INSERT INTO truck (id, plate, brand, driver, is_unload) VALUES (?, ?, ?, ?, ?)";
        Object[] params = {
            truck.getId().toString(),
            truck.getPlate().getPlate(),
            truck.getBrand().getBrand(),
            truck.getDriver().getDriver(),
            truck.isUnload()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Truck inserted succesfully");
        } else {
            System.out.println("Truck not inserted");
        }
        return truck;
    }

    @Override
    public Optional<Truck> find(UUID id) {
        String sql = "SELECT * FROM truck WHERE id=?";
        Object[] params = { id.toString() };
        Truck truck = jdbcTemplate.queryForObject(sql, truckRowMapper, params);
        if (truck != null) {
            System.out.println("Truck finded successfully.");
        } else {
            System.out.println("No truck has been founded.");
        }
        return Optional.ofNullable(truck);
    }

    @Override
    public Page<Truck> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM truck";
        List<Truck> trucks = jdbcTemplate.query(sql, truckRowMapper);
        return new PageImpl<>(trucks, pageRequest, trucks.size());
    }

    @Override
    public Truck update(UUID id, Truck truck) {
        String sql = "UPDATE truck SET plate=?, brand=?, driver=?, is_unload=? WHERE id=?";
        Object[] params = {
            truck.getPlate().getPlate(),
            truck.getBrand().getBrand(),
            truck.getDriver().getDriver(),
            truck.isUnload(),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Update successfully.");
        } else {
            System.out.println("No truck has been updated.");
        }
        return truck;
    }

    @Override
    public Truck delete(UUID id) {
        Optional<Truck> opTruck = find(id);
        if (opTruck.isPresent()) {
            String sql = "DELETE FROM truck WHERE id=?";
            Object[] params = { id.toString() };
            int result = jdbcTemplate.update(sql, params);
            if (result > 0) {
                sql = "DELETE FROM truck_access_port WHERE id_truck=?";
                result = jdbcTemplate.update(sql, params);
                if (result > 0) {
                    System.out.println("Deleted successfully.");
                } else {
                    System.out.println("No access do operation has been deleted.");
                }
            } else {
                System.out.println("No truck has been deleted.");
            }
            return opTruck.get();
        }
        return null;
    }

    @Override
    public List<Truck> deleteAll() {
        String sql = "SELECT * FROM truck";
        List<Truck> trucks = jdbcTemplate.query(sql, truckRowMapper);
        sql = "DELETE FROM truck";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM truck_access_port";
        jdbcTemplate.update(sql);
        return trucks;
    }
}
