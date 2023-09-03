package com.trucks.domain.entity;

import java.util.UUID;
import com.trucks.domain.valueobject.Plate;
import com.trucks.domain.valueobject.Brand;
import com.trucks.domain.valueobject.Driver;

import com.trucks.utils.TruckUtils;

public class Truck {

    private UUID id;
    private Plate plate;
    private Brand brand;
    private Driver driver;
    private boolean isUnload;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Plate getPlate() {
        return this.plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean isUnload() {
        return this.isUnload;
    }

    public void setUnload(boolean isUnload) {
        this.isUnload = isUnload;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public static Truck random() {
        Truck truck = new Truck();
        truck.setId(UUID.randomUUID());
        truck.setPlate(Plate.random());
        truck.setBrand(Brand.random());
        truck.setUnload(TruckUtils.generateRandomNumber(1) % 2 == 0);
        truck.setDriver(Driver.random());
        return truck;
    }
}
