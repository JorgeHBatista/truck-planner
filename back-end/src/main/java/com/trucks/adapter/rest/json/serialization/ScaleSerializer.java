package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.trucks.utils.TruckUtils;

import java.util.List;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Scale;
import com.trucks.domain.entity.Operation;

public class ScaleSerializer extends StdSerializer<Scale> {

    private OperationSerializer operationSerializer = new OperationSerializer();

    public ScaleSerializer() {
        this(null);
    }

    public ScaleSerializer(Class<Scale> t) {
        super(t);
    }

    @Override
    public void serialize(Scale entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDSCALE, entity.getId().toString());
        generator.writeStringField(JsonFields.STARTINGTIME, TruckUtils.localDateTimeToString(entity.getStartingTime()));
        generator.writeStringField(JsonFields.FINISHINGTIME, TruckUtils.localDateTimeToString(entity.getFinishingTime()));
        generator.writeArrayFieldStart(JsonFields.OPERATIONS);
        List<Operation> operations = entity.getOperations();
        if (operations != null && !operations.isEmpty()) {
            for (Operation operation : operations) {
                try {
                    operationSerializer.serialize(operation, generator, provider);
                } catch (Exception e) {
                    System.out.println("Error while serializing operation: " + e.getMessage());
                }
            }
        }
        generator.writeEndArray();
        generator.writeEndObject();
    }
}