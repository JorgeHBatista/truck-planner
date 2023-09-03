package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import com.trucks.domain.entity.Scale;

public class ScaleRowMapper implements RowMapper<Scale> {

    @Override
    public Scale mapRow(ResultSet rs, int rowNum) throws SQLException {

        Scale scale = new Scale();
        scale.setId(UUID.fromString(rs.getString("id")));
        scale.setStartingTime(rs.getObject("starting_time", LocalDateTime.class));
        scale.setFinishingTime(rs.getObject("finishing_time", LocalDateTime.class));
        return scale;
    }
}