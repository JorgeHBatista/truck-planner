package com.trucks.adapter.timescaledb.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.sql.Timestamp;

import com.trucks.utils.TruckUtils;

import com.trucks.adapter.timescaledb.mapper.AccessRowMapper;
import com.trucks.application.port.AccessRepository;
import com.trucks.domain.entity.Access;
import com.trucks.domain.entity.Truck;
import com.trucks.domain.entity.Port;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AccessTimescaleRepository implements AccessRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    AccessRowMapper accessRowMapper = new AccessRowMapper();

    @Override
    public Access save(Access access) {
        String sql = "INSERT INTO truck_access_port (id, id_truck, id_port, " +
         "entry_date, exit_date, entry_point, exit_point, " + 
         "identification_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
            access.getId().toString(),
            access.getTruckID().toString(),
            access.getPortID().toString(),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(access.getEntryDate())),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(access.getExitDate())),
            access.getEntryPoint().toString(),
            access.getExitPoint().toString(),
            access.getIdentificationType().toString()
            
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Access inserted succesfully");
        } else {
            System.out.println("Access not inserted");
        }
        return access;
    }

    @Override
    public Page<Access> findAll(PageRequest pageRequest) {
        String sql = "SELECT * FROM truck_access_port";
        List<Access> accesses = jdbcTemplate.query(sql, accessRowMapper);
        return new PageImpl<>(accesses, pageRequest, accesses.size());
    }

    @Override
    public Access update(UUID id, Access access) {
        String sql = "UPDATE truck_access_port SET id=?, id_truck=?, id_port=?, " +
        "entry_date=?, exit_date=?, entry_point=?, exit_point=?, " + 
        "identification_type=? WHERE id=?";
        Object[] params = {
            access.getId().toString(),
            access.getTruckID().toString(),
            access.getPortID().toString(),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(access.getEntryDate())),
            Timestamp.valueOf(TruckUtils.localDateTimeToString(access.getExitDate())),
            access.getEntryPoint().toString(),
            access.getExitPoint().toString(),
            access.getIdentificationType().toString(),
            id.toString()
        };
        if (jdbcTemplate.update(sql, params) == 1) {
            System.out.println("Update successfully.");
        } else {
            System.out.println("No access has been updated.");
        }
        return access;
    }

    @Override
    public List<Access> deleteAll() {
        String sql = "SELECT * FROM truck_access_port";
        List<Access> accesses = jdbcTemplate.query(sql, accessRowMapper);
        sql = "DELETE FROM truck_access_port";
        jdbcTemplate.update(sql);
        return accesses;
    }
}
