package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.util.List;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Berth;

public class BerthSerializer extends StdSerializer<Berth> {

    private PortSerializer portSerializer = new PortSerializer();
    private VesselSerializer vesselSerializer = new VesselSerializer();

    public BerthSerializer() {
        this(null);
    }

    public BerthSerializer(Class<Berth> t) {
        super(t);
    }

    @Override
    public void serialize(Berth entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDBERTH, entity.getId().toString());
        generator.writeStringField(JsonFields.IDPORT, entity.getPortID().toString());
        generator.writeStringField(JsonFields.IDVESSEL, entity.getVesselID().toString());
        generator.writeEndObject();
    }
}