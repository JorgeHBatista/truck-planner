package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Truck;

public class TruckSerializer extends StdSerializer<Truck> {

    public TruckSerializer() {
        this(null);
    }

    public TruckSerializer(Class<Truck> t) {
        super(t);
    }

    @Override
    public void serialize(Truck entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDTRUCK, entity.getId().toString());
        generator.writeStringField(JsonFields.PLATE, entity.getPlate().getPlate());
        generator.writeStringField(JsonFields.BRAND, entity.getBrand().getBrand());
        generator.writeStringField(JsonFields.DRIVER, entity.getDriver().getDriver());
        generator.writeStringField(JsonFields.UNLOAD, String.valueOf(entity.isUnload()));
        generator.writeEndObject();
    }
}
