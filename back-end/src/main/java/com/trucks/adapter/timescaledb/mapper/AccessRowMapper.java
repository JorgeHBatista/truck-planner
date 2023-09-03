package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.trucks.adapter.timescaledb.repository.TruckTimescaleRepository;
import com.trucks.adapter.timescaledb.repository.PortTimescaleRepository;

import com.trucks.domain.entity.Access;
import com.trucks.domain.valueobject.Plate;
import com.trucks.domain.valueobject.Brand;
import com.trucks.domain.valueobject.Driver;
import com.trucks.domain.valueobject.PortName;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.EntryPoint;
import com.trucks.domain.valueobject.ExitPoint;
import com.trucks.domain.valueobject.IdentificationType;

public class AccessRowMapper implements RowMapper<Access> {

    @Override
    public Access mapRow(ResultSet rs, int rowNum) throws SQLException {

        Access access = new Access();

        access.setId(UUID.fromString(rs.getString("id")));
        access.setTruckID(UUID.fromString(rs.getString("id_truck")));
        access.setPortID(UUID.fromString(rs.getString("id_port")));
        access.setEntryDate(rs.getTimestamp("entry_date").toLocalDateTime());
        access.setExitDate(rs.getTimestamp("exit_date").toLocalDateTime());
        access.setEntryPoint(EntryPoint.valueOf(rs.getString("entry_point")));
        access.setExitPoint(ExitPoint.valueOf(rs.getString("exit_point")));
        access.setIdentificationType(IdentificationType.valueOf(rs.getString("identification_type")));
        return access;
    }
}
