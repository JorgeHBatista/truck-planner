package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.trucks.domain.entity.Operation;
import com.trucks.domain.valueobject.Cargo;
import com.trucks.domain.valueobject.OperationType;
import com.trucks.domain.valueobject.Quantity;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class OperationDeserializer extends UllJsonDeserializer<Operation> {

    @Override
    protected Operation parse(JsonNode node) {
		Operation operation = new Operation();
        operation.setId(UUID.fromString(node.get(JsonFields.IDOPERATION).asText()));
        operation.setCargo(Cargo.valueOf(node.get(JsonFields.CARGO).asText()));
        operation.setType(OperationType.valueOf(node.get(JsonFields.OPERATIONTYPE).asText()));
        operation.setQuantity(new Quantity(node.get(JsonFields.QUANTITY).asInt()));
        return operation;
    }

	@Override
	protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validateCargo(json));
        errors.addAll(this.validateType(json));
        errors.addAll(this.validateQuantity(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDOPERATION)) {
            final String id = json.get(JsonFields.IDOPERATION).textValue();
            try {
                UUID.fromString(id);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.IDOPERATION));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected id field on json file"));
        }
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
