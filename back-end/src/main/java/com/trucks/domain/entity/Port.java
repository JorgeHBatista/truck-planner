package com.trucks.domain.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.PortName;

public class Port {

    private UUID id;
    private PortName name;
    private Coordinate coordinate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PortName getName() {
        return name;
    }

    public void setName(PortName name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static Port random() {
        Random r = new Random();
        Port port = new Port();
        port.setId(UUID.randomUUID());
        port.setName(PortName.random());
        Coordinate coordinate = Coordinate.random();
        port.setCoordinate(coordinate);
        return port;
    }
}
