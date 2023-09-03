package com.trucks.adapter.rest.json.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trucks.utils.TruckUtils;

import com.trucks.adapter.rest.json.serialization.TruckSerializer;
import com.trucks.adapter.rest.json.serialization.PortSerializer;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trucks.adapter.rest.json.JsonFields;
import com.trucks.domain.entity.Access;

public class AccessSerializer extends StdSerializer<Access> {

    private TruckSerializer truckSerializer = new TruckSerializer();
    private PortSerializer portSerializer = new PortSerializer();

    public AccessSerializer() {
        this(null);
    }

    public AccessSerializer(Class<Access> t) {
        super(t);
    }

    @Override
    public void serialize(Access entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField(JsonFields.IDACCESS, entity.getId().toString());
        generator.writeStringField(JsonFields.IDTRUCK, entity.getTruckID().toString());
        generator.writeStringField(JsonFields.IDPORT, entity.getPortID().toString());
        generator.writeStringField(JsonFields.ENTRYDATE, TruckUtils.localDateTimeToString(entity.getEntryDate()));
        generator.writeStringField(JsonFields.EXITDATE, TruckUtils.localDateTimeToString(entity.getExitDate()));
        generator.writeStringField(JsonFields.ENTRYPOINT, entity.getEntryPoint().toString());
        generator.writeStringField(JsonFields.EXITPOINT, entity.getExitPoint().toString());
        generator.writeStringField(JsonFields.IDENTIFICATIONTYPE, entity.getIdentificationType().toString());
        generator.writeEndObject();
    }
}
