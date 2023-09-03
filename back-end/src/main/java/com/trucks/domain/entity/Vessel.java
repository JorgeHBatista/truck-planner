package com.trucks.domain.entity;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Random;

public class Vessel {
    
    private UUID id;
    private List<Scale> scales;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Scale> getScales() {
        return scales;
    }

    public void setScales(List<Scale> scales) {
        this.scales = scales;
    }

    public static Vessel random() {
        Vessel vessel = new Vessel();
        vessel.setId(UUID.randomUUID());
        List<Scale> scales = new ArrayList<Scale>();
        Random rand = new Random();
        int amount = rand.nextInt(11);
        for (int i = 0; i < amount; i++) {
            scales.add(Scale.random());
        }
        vessel.setScales(scales);
        return vessel;
    }
}
