package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.trucks.domain.entity.Access;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.trucks.domain.entity.Truck;
import com.trucks.domain.entity.Port;
import com.trucks.domain.valueobject.EntryPoint;
import com.trucks.domain.valueobject.ExitPoint;
import com.trucks.domain.valueobject.IdentificationType;
import com.trucks.adapter.rest.json.serialization.TruckDeserializer;
import com.trucks.adapter.rest.json.serialization.PortDeserializer;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class AccessDeserializer extends UllJsonDeserializer<Access> {

    private TruckDeserializer truckDeserializer = new TruckDeserializer();
    private PortDeserializer portDeserializer = new PortDeserializer();

    @Override
    protected Access parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDACCESS).asText());
        UUID truckID = UUID.fromString(node.get(JsonFields.IDTRUCK).asText());
        UUID portID = UUID.fromString(node.get(JsonFields.IDPORT).asText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime entryDate = LocalDateTime.parse(node.get(JsonFields.ENTRYDATE).asText(), formatter);
        LocalDateTime exitDate = LocalDateTime.parse(node.get(JsonFields.EXITDATE).asText(), formatter);
        EntryPoint entryPoint = EntryPoint.valueOf(node.get(JsonFields.ENTRYPOINT).asText());
        ExitPoint exitPoint = ExitPoint.valueOf(node.get(JsonFields.EXITPOINT).asText());
        IdentificationType identificationType = IdentificationType.valueOf(node.get(JsonFields.IDENTIFICATIONTYPE).asText());
        Access access = new Access();
        access.setId(id);
        access.setEntryDate(entryDate); 
        access.setExitDate(exitDate);
        access.setEntryPoint(entryPoint);
        access.setExitPoint(exitPoint);
        access.setIdentificationType(identificationType);
        access.setTruckID(truckID);
        access.setPortID(portID);
        return access;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        System.out.println(json.toString());
        errors.addAll(this.validateId(json));
        errors.addAll(this.validateEntryDate(json));
        errors.addAll(this.validateExitDate(json));
        errors.addAll(this.validateEntryPoint(json));
        errors.addAll(this.validateExitPoint(json));
        errors.addAll(this.validateIdentificationType(json));
        errors.addAll(this.validateTruck(json));
        errors.addAll(this.validatePort(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDACCESS)) {
            final String id = json.get(JsonFields.IDACCESS).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDTRUCK));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateEntryDate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.ENTRYDATE)) {
            final String entryDate = json.get(JsonFields.ENTRYDATE).textValue();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime.parse(entryDate, formatter);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.ENTRYDATE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected arrival time field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateExitDate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.EXITDATE)) {
            final String exitDate = json.get(JsonFields.EXITDATE).textValue();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime.parse(exitDate, formatter);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.EXITDATE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected arrival time field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateEntryPoint(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.ENTRYPOINT)) {
            final String entryPoint = json.get(JsonFields.ENTRYPOINT).textValue();
            try {
                EntryPoint.valueOf(entryPoint);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.ENTRYPOINT));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected entry point field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateExitPoint(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.EXITPOINT)) {
            final String exitPoint = json.get(JsonFields.EXITPOINT).textValue();
            try {
                ExitPoint.valueOf(exitPoint);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.EXITPOINT));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected exit point field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateIdentificationType(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDENTIFICATIONTYPE)) {
            final String identificationType = json.get(JsonFields.IDENTIFICATIONTYPE).textValue();
            try {
                IdentificationType.valueOf(identificationType);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDENTIFICATIONTYPE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected identification type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateTruck(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(truckDeserializer.validateId(json));
        return errors;
    }

    protected List<ApiSubError> validatePort(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(portDeserializer.validateId(json));
        return errors;
    }
}
