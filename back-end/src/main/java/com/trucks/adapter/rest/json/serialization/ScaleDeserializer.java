package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trucks.domain.entity.Scale;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Iterator;
import java.util.Optional;
import org.json.simple.JSONArray;
import com.trucks.domain.valueobject.Cargo;
import com.trucks.domain.valueobject.OperationType;
import com.trucks.domain.valueobject.Quantity;
import com.trucks.domain.entity.Operation;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Berth;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class ScaleDeserializer extends UllJsonDeserializer<Scale> {

    private OperationDeserializer operationDeserializer = new OperationDeserializer();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected Scale parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDSCALE).asText());
        LocalDateTime startingTime = LocalDateTime.parse(node.get(JsonFields.STARTINGTIME).asText(), this.formatter);
        LocalDateTime finishingTime = LocalDateTime.parse(node.get(JsonFields.FINISHINGTIME).asText(), this.formatter);
        List<Operation> operations = new ArrayList<>();
        ArrayNode arrayNode = (ArrayNode) node.get(JsonFields.OPERATIONS);
        Operation operation = new Operation();
        for (final JsonNode objNode : arrayNode) {
            operation = operationDeserializer.parse(objNode);
            operations.add(operation);
        }
        Scale scale = new Scale();
        scale.setId(id);
        scale.setStartingTime(startingTime);
        scale.setFinishingTime(finishingTime);
        scale.setOperations(operations);
        return scale;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validateStartingTime(json));
        errors.addAll(this.validateFinishingTime(json));
        errors.addAll(this.validateOperations(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDSCALE)) {
            final String id = json.get(JsonFields.IDSCALE).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDSCALE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateStartingTime(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.STARTINGTIME)) {
            String startingTime = json.get(JsonFields.STARTINGTIME).asText();
            try {
                LocalDateTime.parse(startingTime, this.formatter);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.STARTINGTIME));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected starting time type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateFinishingTime(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.FINISHINGTIME)) {
            String finishingTime = json.get(JsonFields.FINISHINGTIME).asText();
            try {
                LocalDateTime.parse(finishingTime, this.formatter);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.FINISHINGTIME));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected finishing time type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateOperations(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.OPERATIONS)) {
            JsonNode operations = json.get(JsonFields.OPERATIONS);
            if (operations.isArray()) {
                for (JsonNode operation : operations) {
                    errors.addAll(this.validateOperation(operation));
                }
            } else {
                errors.add(new ApiSubErrorMessage(
                        "Expected operations type field on json field"));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected operations type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateOperation(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateCargo(json));
        errors.addAll(this.validateType(json));
        errors.addAll(this.validateQuantity(json));
        return errors;
    }

    protected List<ApiSubError> validateCargo(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.CARGO)) {
            String cargo = json.get(JsonFields.CARGO).asText();
            try {
                Cargo.valueOf(cargo);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(), JsonFields.CARGO));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected cargo type field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateType(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.OPERATIONTYPE)) {
            String type = json.get(JsonFields.OPERATIONTYPE).asText();
            try {
                OperationType.valueOf(type);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(), JsonFields.OPERATIONTYPE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected operation type field on json field"));
        }
        return errors;
    }   

    protected List<ApiSubError> validateQuantity(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.QUANTITY)) {
            int quantity = json.get(JsonFields.QUANTITY).asInt();
            if (quantity < 0) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        "Quantity must be greater than zero",
                        JsonFields.QUANTITY));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected quantity type field on json field"));
        }
        return errors;
    }
}
