package domain.entity;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import utils.TruckUtils;

public class Berth {

    private UUID id;
    private Port port;
    private Vessel vessel;

    public UUID getId() {
        return this.id;
    }
    
    public Port getPort() {
        return this.port;
    }    

    public Vessel getVessel() {
        return this.vessel;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public boolean equals(Berth other) {
        return this.id.equals(other.getId()) &&
                this.port.equals(other.getPort()) &&
                this.vessel.equals(other.getVessel());
    }

    public static Berth random() {
        Berth berth = new Berth();
        berth.setId(UUID.randomUUID());
        berth.setPort(Port.random());
        berth.setVessel(Vessel.random());
        return berth;
    }


}