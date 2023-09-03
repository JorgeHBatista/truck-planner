package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.time.LocalDateTime;

import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Scale;

public class VesselRowMapper implements RowMapper<Vessel> {

    @Override
    public Vessel mapRow(ResultSet rs, int rowNum) throws SQLException {

        Vessel vessel = new Vessel();
        vessel.setId(UUID.fromString(rs.getString("id")));
        return vessel;

    }
}
