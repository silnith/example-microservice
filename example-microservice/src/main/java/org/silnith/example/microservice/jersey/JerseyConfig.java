package org.silnith.example.microservice.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.silnith.example.microservice.jaxrs.TransactionController;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super(TransactionController.class);
    }

}
