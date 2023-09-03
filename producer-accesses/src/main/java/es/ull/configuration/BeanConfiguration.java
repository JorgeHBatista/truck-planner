package es.ull.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import es.ull.application.AccessGenerator;
import java.text.ParseException;

@Configuration
public class BeanConfiguration {

    @Value("${producer.numberOfStartingAccesses}")
    private int NUMBER_OF_STARTING_ACCESSES;
    @Value("${producer.entryDate}")
    private String ENTRY_DATE;
    @Value("${producer.exitDate}")
    private String EXIT_DATE;
    @Value("${producer.entryPoint}")
    private String ENTRY_POINT;
    @Value("${producer.exitPoint}")
    private String EXIT_POINT;
    @Value("${producer.identificationType}")
    private String IDENTIFICATION_TYPE;
    @Value("${producer.period}")
    private int PERIOD;
    @Value("${producer.timeFactor}")
    private long TIME_FACTOR;
    @Value("${producer.startingDate}")
    private String STARTING_DATE;
    @Value("${producer.endingDate}")
    private String ENDING_DATE;

    @Bean
    public AccessGenerator accessGenerator() throws ParseException {
        return new AccessGenerator(
            NUMBER_OF_STARTING_ACCESSES,
            ENTRY_DATE,
            EXIT_DATE,
            ENTRY_POINT,
            EXIT_POINT,
            IDENTIFICATION_TYPE,
            PERIOD,
            TIME_FACTOR,
            STARTING_DATE,
            ENDING_DATE
        );
    }
}
