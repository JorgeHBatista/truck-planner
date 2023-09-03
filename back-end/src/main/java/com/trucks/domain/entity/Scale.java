package com.trucks.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Scale {

    private UUID id;
    private LocalDateTime startingTime;
    private LocalDateTime finishingTime;
    private List<Operation> operations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalDateTime getFinishingTime() {
        return finishingTime;
    }

    public void setFinishingTime(LocalDateTime finishingTime) {
        this.finishingTime = finishingTime;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operation) {
        this.operations = operation;
    }

    public static Scale random() {
        Scale scale = new Scale();
        scale.setId(UUID.randomUUID());
        scale.setStartingTime(LocalDateTime.now());
        scale.setFinishingTime(LocalDateTime.now());
        List<Operation> operations = new ArrayList<Operation>();
        Random rand = new Random();
        int amount = rand.nextInt(11);
        for (int i = 0; i < amount; i++) {
            operations.add(Operation.random());
        }
        scale.setOperations(operations);
        return scale;
    }
}
