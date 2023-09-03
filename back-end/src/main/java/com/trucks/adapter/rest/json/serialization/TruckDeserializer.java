package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.trucks.domain.entity.Truck;
import com.trucks.domain.valueobject.Plate;
import com.trucks.domain.valueobject.Brand;
import com.trucks.domain.valueobject.Driver;

import es.ull.utils.rest.serialization.UllJsonDeserializer;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.trucks.adapter.rest.json.JsonFields;

import es.ull.utils.rest.error.ApiSubError;
import es.ull.utils.rest.error.ApiSubErrorMessage;
import es.ull.utils.rest.error.ApiSubErrorMessageRejectedValueField;

public class TruckDeserializer extends UllJsonDeserializer<Truck> {

    @Override
    protected Truck parse(JsonNode node) {
        UUID id = UUID.fromString(node.get(JsonFields.IDTRUCK).asText());
        Plate plate = new Plate(node.get(JsonFields.PLATE).asText());
        Brand brand = new Brand(node.get(JsonFields.BRAND).asText());
        Driver driver = new Driver(node.get(JsonFields.DRIVER).asText());
        boolean isUnload = node.get(JsonFields.UNLOAD).asBoolean();
        Truck truck = new Truck();
        truck.setId(id);
        truck.setPlate(plate);
        truck.setBrand(brand);
        truck.setDriver(driver);
        truck.setUnload(isUnload);
        return truck;
    }

    @Override
    protected Optional<List<ApiSubError>> validate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        errors.addAll(this.validateId(json));
        errors.addAll(this.validatePlate(json));
        errors.addAll(this.validateBrand(json));
        errors.addAll(this.validateDriver(json));
        errors.addAll(this.validateUnload(json));
        return (errors.isEmpty()) ? Optional.empty() : Optional.of(errors);
    }

    protected List<ApiSubError> validateId(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.IDTRUCK)) {
            final String id = json.get(JsonFields.IDTRUCK).textValue();
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

    protected List<ApiSubError> validatePlate(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.PLATE)) {
            final String plate = json.get(JsonFields.PLATE).textValue();
            try {
                new Plate(plate);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.PLATE));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected plate field on json file"));
        }
        return errors;
    }

    protected List<ApiSubError> validateBrand(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.BRAND)) {
            final String brand = json.get(JsonFields.BRAND).textValue();
            try {
                new Brand(brand);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.BRAND));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected brand field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateDriver(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.DRIVER)) {
            final String driver = json.get(JsonFields.DRIVER).textValue();
            try {
                new Driver(driver);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.DRIVER));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected driver field on json field"));
        }
        return errors;
    }

    protected List<ApiSubError> validateUnload(JsonNode json) {
        List<ApiSubError> errors = new ArrayList<>();
        if (json.has(JsonFields.UNLOAD)) {
            final String unload = json.get(JsonFields.UNLOAD).textValue();
            try {
                Boolean isUnload = Boolean.parseBoolean(unload);
            } catch (Exception exception) {
                errors.add(new ApiSubErrorMessageRejectedValueField(
                        exception.getMessage(),
                        JsonFields.UNLOAD));
            }
        } else {
            errors.add(new ApiSubErrorMessage(
                    "Expected unload field on json field"));
        }
        return errors;
    }
}
