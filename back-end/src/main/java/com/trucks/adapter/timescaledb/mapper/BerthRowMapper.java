package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.trucks.domain.entity.Berth;
import com.trucks.domain.entity.Port;
import com.trucks.domain.entity.Vessel;

public class BerthRowMapper implements RowMapper<Berth> {

    @Override
    public Berth mapRow(ResultSet rs, int rowNum) throws SQLException {

        Berth berth = new Berth();
        berth.setId(UUID.fromString(rs.getString("id")));
        berth.setPortID(UUID.fromString(rs.getString("id_port")));
        berth.setVesselID(UUID.fromString(rs.getString("id_vessel")));
        return berth;
    }
}
