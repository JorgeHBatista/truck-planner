package domain.entity;

import java.util.UUID;
import domain.valueobject.EntryPoint;
import domain.valueobject.ExitPoint;
import domain.valueobject.IdentificationType;
import java.time.LocalDateTime;

public class Access {

    private UUID id;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private EntryPoint entryPoint;
    private ExitPoint exitPoint;
    private IdentificationType identificationType;
    private Truck truck;
    private Port port;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EntryPoint getEntryPoint() {
        return this.entryPoint;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public ExitPoint getExitPoint() {
        return this.exitPoint;
    }

    public void setExitPoint(ExitPoint exitPoint) {
        this.exitPoint = exitPoint;
    }

    public LocalDateTime getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public IdentificationType getIdentificationType() {
        return this.identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public Truck getTruck() {
        return this.truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Port getPort() {
        return this.port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public static Access random() {
        Access access = new Access();
        access.setId(UUID.randomUUID());
        access.setEntryPoint(EntryPoint.random());
        access.setExitPoint(ExitPoint.random());
        access.setEntryDate(LocalDateTime.now());
        access.setExitDate(LocalDateTime.now());
        access.setIdentificationType(IdentificationType.random());
        access.setTruck(Truck.random());
        access.setPort(Port.random());
        return access;
    }
}