package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.util.List;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Operation;

public class OperationSerializer extends StdSerializer<Operation> {

    public OperationSerializer() {
        this(null);
    }

    public OperationSerializer(Class<Operation> t) {
        super(t);
    }

    @Override
    public void serialize(Operation entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
      generator.writeStartObject();
      generator.writeStringField(JsonFields.IDOPERATION, entity.getId().toString());
      generator.writeStringField(JsonFields.CARGO, entity.getCargo().toString());
      generator.writeStringField(JsonFields.OPERATIONTYPE, entity.getType().toString());
      generator.writeStringField(JsonFields.QUANTITY, String.valueOf(entity.getQuantity().getValue()));
      generator.writeEndObject();
    }

    public void serializeWithoutObject(Operation entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
      generator.writeStringField(JsonFields.IDOPERATION, entity.getId().toString());
      generator.writeStringField(JsonFields.CARGO, entity.getCargo().toString());
      generator.writeStringField(JsonFields.OPERATIONTYPE, entity.getType().toString());
      generator.writeStringField(JsonFields.QUANTITY, String.valueOf(entity.getQuantity().getValue()));
    }
}