package org.silnith.example.microservice;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * The main class of the application when run stand-alone.  This can also be run as a servlet.
 * 
 * <p>This class is in the root package for the service so that all the packages under it get
 * scanned for dependency injection.  This happens automatically as part of the
 * {@link SpringBootApplication} annotation auto-configuration.
 * 
 * <p>The {@link OpenAPIDefinition} annotation adds documentation to the generated OpenAPI document.
 * The annotation is here because its contents apply to the entire service.
 */
@OpenAPIDefinition(info = @Info(
        title = "Example Microservice",
        version = "0.0.1",
        contact = @Contact(name = "Kent Rosenkoetter", email = "silnith@gmail.com"),
        description = "An example template for a microservice written in Java.  This can be used to model and guide the creation of other services."))
@SpringBootApplication
public class Microservice {
    
    /**
     * A system clock.  Injected for testing purposes.
     * 
     * @return the system clock
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
    
    /**
     * A random number generator.  Injected for testing purposes.
     * 
     * @return a random number generator
     */
    @Bean
    public Random random() {
        return new SecureRandom();
    }
    
    /**
     * An executor service that supports scheduling.
     * 
     * @return a scheduled executor service.
     */
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }
    
    /**
     * The main entry point for the service.  Alternatively, this can be run as a servlet.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Microservice.class, args);
    }
    
}
