package com.trucks.domain.entity;

import java.util.UUID;

import com.trucks.domain.valueobject.Cargo;
import com.trucks.domain.valueobject.OperationType;
import com.trucks.domain.valueobject.Quantity;

public class Operation {

    private UUID id;            // UUID
    private Cargo cargo;        // METALES, AZUFRES, ...
    private OperationType type; // CARGA/DESCARGA
    private Quantity quantity;  // NÚMERO MAYOR QUE 0

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public static Operation random() {
        Operation operation = new Operation();
        operation.setId(UUID.randomUUID());
        operation.setCargo(Cargo.random());
        operation.setType(OperationType.random());
        operation.setQuantity(Quantity.random());
        return operation;
    }

    public boolean equals(Operation operation) {
        return  this.id.equals(operation.getId()) &&
                this.cargo.equals(operation.getCargo()) &&
                this.type.equals(operation.getType()) &&
                this.quantity.equals(operation.getQuantity());
    }
}
