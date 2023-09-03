package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.trucks.domain.entity.Port;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import es.ull.utils.rest.serialization.UllJsonDeserializer;
import com.trucks.adapter.rest.json.JsonFields;

import com.trucks.domain.entity.Port;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.PortName;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class PortDeserializer extends UllJsonDeserializer<Port> {

    @Override
    protected Port parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDPORT).asText());
        String name = new String(node.get(JsonFields.NAME).asText());
        PortName portName = new PortName(name);
        String coordinate = (node.get(JsonFields.COORDINATES).asText());
        double latitude = Double.parseDouble(coordinate.split(", ")[0]);
        double longitude = Double.parseDouble(coordinate.split(", ")[1]);
        Coordinate coord = new Coordinate(latitude, longitude);
        Port port = new Port();
        port.setId(id);
        port.setName(portName);
        port.setCoordinate(coord);
        return port;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validateName(json));
        errors.addAll(this.validateCoordinate(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDPORT)) {
            final String id = json.get(JsonFields.IDPORT).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDPORT));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateName(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.NAME)) {
            try {
                final String name = json.get(JsonFields.NAME).textValue();
                new PortName(name);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.NAME));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected name field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateCoordinate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.COORDINATES)) {
            String coordinate = (json.get(JsonFields.COORDINATES).asText());
            try {
                double latitude = Double.parseDouble(coordinate.split(", ")[0]);
                double longitude = Double.parseDouble(coordinate.split(", ")[1]);
                new Coordinate(latitude, longitude);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.COORDINATES));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected coordinate field on json field"));
        }
        return errors;
    }
}
