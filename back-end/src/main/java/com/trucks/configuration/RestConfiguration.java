package com.trucks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.trucks.domain.entity.Port;
import com.trucks.domain.entity.Truck;
import com.trucks.domain.entity.Access;
import com.trucks.domain.entity.Scale;
import com.trucks.domain.entity.Berth;
import com.trucks.domain.entity.Vessel;
import com.trucks.domain.entity.Operation;

import es.ull.utils.rest.exception.UllExceptionHandler;
import com.trucks.adapter.rest.json.serialization.TruckDeserializer;
import com.trucks.adapter.rest.json.serialization.TruckSerializer;
import com.trucks.adapter.rest.json.serialization.PortDeserializer;
import com.trucks.adapter.rest.json.serialization.PortSerializer;
import com.trucks.adapter.rest.json.serialization.AccessDeserializer;
import com.trucks.adapter.rest.json.serialization.AccessSerializer;
import com.trucks.adapter.rest.json.serialization.ScaleDeserializer;
import com.trucks.adapter.rest.json.serialization.ScaleSerializer;
import com.trucks.adapter.rest.json.serialization.BerthDeserializer;
import com.trucks.adapter.rest.json.serialization.BerthSerializer;
import com.trucks.adapter.rest.json.serialization.VesselDeserializer;
import com.trucks.adapter.rest.json.serialization.VesselSerializer;
import com.trucks.adapter.rest.json.serialization.OperationDeserializer;
import com.trucks.adapter.rest.json.serialization.OperationSerializer;


@Configuration
public class RestConfiguration {

    @Bean
    public Module dynamoDemoEntityDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Truck.class, new TruckDeserializer());
        module.addSerializer(Truck.class, new TruckSerializer());
        module.addDeserializer(Port.class, new PortDeserializer());
        module.addSerializer(Port.class, new PortSerializer());
        module.addDeserializer(Access.class, new AccessDeserializer());
        module.addSerializer(Access.class, new AccessSerializer());
        module.addDeserializer(Scale.class, new ScaleDeserializer());
        module.addSerializer(Scale.class, new ScaleSerializer());
        module.addDeserializer(Berth.class, new BerthDeserializer());
        module.addSerializer(Berth.class, new BerthSerializer());
        module.addDeserializer(Vessel.class, new VesselDeserializer());
        module.addSerializer(Vessel.class, new VesselSerializer());
        module.addDeserializer(Operation.class, new OperationDeserializer());
        module.addSerializer(Operation.class, new OperationSerializer());
        return module;
    }

    @Bean
    public ResponseEntityExceptionHandler responseEntityExceptionHandler() {
        return new UllExceptionHandler();
    }
}
