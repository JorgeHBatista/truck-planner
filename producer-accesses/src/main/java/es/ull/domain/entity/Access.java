package es.ull.domain.entity;

import java.util.UUID;
import es.ull.domain.valueobject.EntryPoint;
import es.ull.domain.valueobject.ExitPoint;
import es.ull.domain.valueobject.IdentificationType;
import java.time.LocalDateTime;
import es.ull.utils.TruckUtils;

public class Access {

    private UUID id;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private EntryPoint entryPoint;
    private ExitPoint exitPoint;
    private IdentificationType identificationType;
    private UUID truckID;
    private UUID portID;

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

    public UUID getTruckID() {
        return this.truckID;
    }

    public void setTruckID(UUID truckID) {
        this.truckID = truckID;
    }

    public UUID getPortID() {
        return this.portID;
    }

    public void setPortID(UUID portID) {
        this.portID = portID;
    }

    public static Access random() {
        Access access = new Access();
        access.setId(UUID.randomUUID());
        access.setEntryPoint(EntryPoint.random());
        access.setExitPoint(ExitPoint.random());
        access.setEntryDate(LocalDateTime.now());
        access.setExitDate(LocalDateTime.now());
        access.setIdentificationType(IdentificationType.random());
        access.setTruckID(UUID.randomUUID());
        access.setPortID(UUID.randomUUID());
        return access;
    }

    public String toJson() {
        return "{" +
            " idAccess='" + getId().toString() + "'" +
            ", idTruck='" + getTruckID().toString() + "'" +
            ", idPort='" + getPortID().toString() + "'" +
            ", entryDate='" + TruckUtils.localDateTimeToString(getEntryDate()) + "'" +
            ", exitDate='" + TruckUtils.localDateTimeToString(getExitDate()) + "'" +
            ", entryPoint='" + getEntryPoint().toString() + "'" +
            ", exitPoint='" + getExitPoint().toString() + "'" +
            ", identificationType='" + getIdentificationType().toString() + "'" +
            "}";
    }
}
