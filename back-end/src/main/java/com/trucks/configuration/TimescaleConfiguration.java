package com.trucks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trucks.adapter.timescaledb.repository.TruckTimescaleRepository;
import com.trucks.application.port.TruckRepository;

import com.trucks.adapter.timescaledb.repository.AccessTimescaleRepository;
import com.trucks.application.port.AccessRepository;

import com.trucks.adapter.timescaledb.repository.PortTimescaleRepository;
import com.trucks.application.port.PortRepository;

import com.trucks.adapter.timescaledb.repository.VesselTimescaleRepository;
import com.trucks.application.port.VesselRepository;

import com.trucks.adapter.timescaledb.repository.ScaleTimescaleRepository;
import com.trucks.application.port.ScaleRepository;

import com.trucks.adapter.timescaledb.repository.BerthTimescaleRepository;
import com.trucks.application.port.BerthRepository;

import com.trucks.adapter.timescaledb.repository.OperationTimescaleRepository;
import com.trucks.application.port.OperationRepository;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TimescaleConfiguration {
    
    @Bean
    public PortRepository portRepository() {
        return new PortTimescaleRepository();
    }
    
    @Bean
    public TruckRepository truckRepository() {
        return new TruckTimescaleRepository();
    }

    @Bean
    public AccessRepository accessRepository() {
        return new AccessTimescaleRepository();
    }

    @Bean
    public VesselRepository vesselRepository() {
        return new VesselTimescaleRepository();
    }

    @Bean
    public ScaleRepository scaleRepository() {
        return new ScaleTimescaleRepository();
    }

    @Bean
    public BerthRepository berthRepository() {
        return new BerthTimescaleRepository();
    }

    @Bean
    public OperationRepository operationRepository() {
        return new OperationTimescaleRepository();
    }

    @Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl("jdbc:postgresql://timescaledb:5432/truckplanner");
		driverManagerDataSource.setUsername("postgres");
		driverManagerDataSource.setPassword("password");
		driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
		return driverManagerDataSource;
	}

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
