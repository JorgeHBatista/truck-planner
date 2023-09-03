package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trucks.domain.entity.Vessel;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Berth;
import com.trucks.domain.entity.Scale;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class VesselDeserializer extends UllJsonDeserializer<Vessel> {

    private ScaleDeserializer scaleDeserializer = new ScaleDeserializer();

    @Override
    protected Vessel parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDVESSEL).asText());
        List<Scale> scales = new ArrayList<>();
        ArrayNode arrayNode = (ArrayNode) node.get(JsonFields.SCALES);
        Scale scale = new Scale();
        for (final JsonNode objNode : arrayNode) {
            scale = scaleDeserializer.parse(objNode);
            scales.add(scale);
        }
        Vessel vessel = new Vessel();
        vessel.setId(id);
        vessel.setScales(scales);
        return vessel;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validateScales(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDVESSEL)) {
            final String id = json.get(JsonFields.IDVESSEL).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDVESSEL));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateScales(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.SCALES)) {
            JsonNode scales = json.get(JsonFields.SCALES);
            if (scales.isArray()) {
                for (JsonNode scale : scales) {
                    errors.addAll(this.validateScale(scale));
                }
            } else {
                errors.add(new ApiSubErrorMessage(
                        "Expected scales type field on json field"));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected scales type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateScale(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(scaleDeserializer.validateId(json));
        errors.addAll(scaleDeserializer.validateStartingTime(json));
        errors.addAll(scaleDeserializer.validateFinishingTime(json));
        errors.addAll(scaleDeserializer.validateOperations(json));
        return errors;
    }
}