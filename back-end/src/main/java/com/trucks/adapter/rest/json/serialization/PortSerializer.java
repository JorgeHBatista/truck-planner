package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Port;

public class PortSerializer extends StdSerializer<Port> {

    public PortSerializer() {
        this(null);
    }

    public PortSerializer(Class<Port> t) {
        super(t);
    }

    @Override
    public void serialize(Port entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDPORT, entity.getId().toString());
        generator.writeStringField(JsonFields.NAME, entity.getName().getPortName());
        generator.writeStringField(JsonFields.COORDINATES, entity.getCoordinate().toString());
        generator.writeEndObject();
    }

    public void serializeWithoutObject(Port entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStringField(JsonFields.IDPORT, entity.getId().toString());
        generator.writeStringField(JsonFields.NAME, entity.getName().getPortName());
        generator.writeStringField(JsonFields.COORDINATES, entity.getCoordinate().toString());
    }
}
