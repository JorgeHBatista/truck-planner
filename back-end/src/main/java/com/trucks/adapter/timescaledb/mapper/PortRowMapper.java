package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.trucks.domain.entity.Port;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.PortName;

public class PortRowMapper implements RowMapper<Port> {

    @Override
    public Port mapRow(ResultSet rs, int rowNum) throws SQLException {

        String coordinate = (rs.getString("coordinate"));
        double latitude = Double.parseDouble(coordinate.split(", ")[0]);
        double longitude = Double.parseDouble(coordinate.split(", ")[1]);
        Coordinate coord = new Coordinate(latitude, longitude);

        Port port = new Port();
        port.setId(UUID.fromString(rs.getString("id")));
        port.setCoordinate(coord);
        port.setName(new PortName(rs.getString("name")));

        return port;

    }
}
