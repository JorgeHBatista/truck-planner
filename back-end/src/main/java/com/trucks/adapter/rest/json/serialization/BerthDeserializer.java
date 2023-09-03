package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.trucks.domain.entity.Berth;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.trucks.domain.entity.Port;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.PortName;
import com.trucks.adapter.rest.json.serialization.PortDeserializer;
import com.trucks.adapter.rest.json.serialization.VesselDeserializer;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class BerthDeserializer extends UllJsonDeserializer<Berth> {

    private PortDeserializer portDeserializer = new PortDeserializer();
    private VesselDeserializer vesselDeserializer = new VesselDeserializer();

    @Override
    protected Berth parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDBERTH).asText());
        UUID portID = UUID.fromString(node.get(JsonFields.IDPORT).asText());
        UUID vesselID = UUID.fromString(node.get(JsonFields.IDVESSEL).asText());
        Berth berth = new Berth();
        berth.setId(id);
        berth.setPortID(portID);
        berth.setVesselID(vesselID);
        return berth;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validatePort(json));
        errors.addAll(this.validateVessel(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDBERTH)) {
            final String id = json.get(JsonFields.IDBERTH).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDBERTH));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validatePort(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(portDeserializer.validateId(json));
        return errors;
    }
            
    protected List<ApiSubError> validateVessel(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(vesselDeserializer.validateId(json));
        return errors;
    }
}
