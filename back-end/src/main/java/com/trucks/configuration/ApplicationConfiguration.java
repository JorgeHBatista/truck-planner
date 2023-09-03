package com.trucks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trucks.application.service.PortService;
import com.trucks.application.service.TruckService;
import com.trucks.application.service.AccessService;
import com.trucks.application.service.VesselService;
import com.trucks.application.service.BerthService;
import com.trucks.application.service.ScaleService;
import com.trucks.application.service.OperationService;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public PortService portService() {
        return new PortService();
    }

    @Bean
    public TruckService TruckService() {
        return new TruckService();
    }

    @Bean
    public AccessService AccessService() {
        return new AccessService();
    }

    @Bean
    public VesselService VesselService() {
        return new VesselService();
    }

    @Bean
    public BerthService BerthService() {
        return new BerthService();
    }

    @Bean
    public ScaleService ScaleService() {
        return new ScaleService();
    }

    @Bean
    public OperationService OperationService() {
        return new OperationService();
    }
}
