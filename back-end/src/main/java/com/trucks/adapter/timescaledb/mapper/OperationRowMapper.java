package com.trucks.adapter.timescaledb.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.trucks.domain.entity.Operation;
import com.trucks.domain.valueobject.Cargo;
import com.trucks.domain.valueobject.OperationType;
import com.trucks.domain.valueobject.Quantity;

public class OperationRowMapper implements RowMapper<Operation> {

    @Override
    public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Operation operation = new Operation();
        operation.setId(UUID.fromString(rs.getString("id")));
        operation.setCargo(Cargo.valueOf(rs.getString("cargo")));
        operation.setType(OperationType.valueOf(rs.getString("type")));
        operation.setQuantity(new Quantity(rs.getInt("quantity")));
        return operation;
    }
}
