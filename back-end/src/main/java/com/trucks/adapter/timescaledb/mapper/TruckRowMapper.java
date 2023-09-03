package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.trucks.domain.entity.Truck;
import com.trucks.domain.valueobject.Plate;
import com.trucks.domain.valueobject.Brand;
import com.trucks.domain.valueobject.Driver;

public class TruckRowMapper implements RowMapper<Truck> {

    @Override
    public Truck mapRow(ResultSet rs, int rowNum) throws SQLException {

        Truck truck = new Truck();
        truck.setId(UUID.fromString(rs.getString("id")));
        truck.setPlate(new Plate(rs.getString("plate")));
        truck.setBrand(new Brand(rs.getString("brand")));
        truck.setDriver(new Driver(rs.getString("driver")));
        truck.setUnload(rs.getBoolean("is_unload"));
        return truck;
    }
}