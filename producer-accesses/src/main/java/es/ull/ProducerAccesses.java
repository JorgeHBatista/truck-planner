package es.ull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class ProducerAccesses {

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(ProducerAccesses.class, args);
    }
}
