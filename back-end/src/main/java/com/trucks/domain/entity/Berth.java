package com.trucks.domain.entity;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import com.trucks.utils.TruckUtils;

public class Berth {

    private UUID id;
    private UUID portID;
    private UUID vesselID;

    public UUID getId() {
        return this.id;
    }
    
    public UUID getPortID() {
        return this.portID;
    }    

    public UUID getVesselID() {
        return this.vesselID;
    }

    public void setPortID(UUID port) {
        this.portID = port;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVesselID(UUID vessel) {
        this.vesselID = vessel;
    }

    public boolean equals(Berth other) {
        return this.id.equals(other.getId()) &&
                this.portID.equals(other.getPortID()) &&
                this.vesselID.equals(other.getVesselID());
    }

    public static Berth random() {
        Berth berth = new Berth();
        berth.setId(UUID.randomUUID());
        berth.setPortID(UUID.randomUUID());
        berth.setVesselID(UUID.randomUUID());
        return berth;
    }


}