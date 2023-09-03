package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.util.List;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Scale;

public class VesselSerializer extends StdSerializer<Vessel> {

    private ScaleSerializer scaleSerializer = new ScaleSerializer();

    public VesselSerializer() {
        this(null);
    }

    public VesselSerializer(Class<Vessel> t) {
        super(t);
    }

    @Override
    public void serialize(Vessel entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDVESSEL, entity.getId().toString());
        generator.writeArrayFieldStart(JsonFields.SCALES);
        List<Scale> scales = entity.getScales();
        if (scales != null && !scales.isEmpty()) {
            for (Scale scale : scales) {
                try {
                    scaleSerializer.serialize(scale, generator, provider);
                } catch (Exception e) {
                    System.out.println("Error while serializing scale: " + e.getMessage());
                }
                
            }
        }
        generator.writeEndArray();
        generator.writeEndObject();
    }

    public void serializeWithoutObject(Vessel entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStringField(JsonFields.IDVESSEL, entity.getId().toString());
        generator.writeArrayFieldStart(JsonFields.SCALES);
        List<Scale> scales = entity.getScales();
        if (scales != null && !scales.isEmpty()) {
            for (Scale scale : scales) {
                try {
                    scaleSerializer.serialize(scale, generator, provider);
                } catch (Exception e) {
                    System.out.println("Error while serializing scale: " + e.getMessage());
                }
                
            }
        }
        generator.writeEndArray();
    }
}
