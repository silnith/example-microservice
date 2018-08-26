package org.silnith.example.microservice.jaxrs;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.silnith.example.microservice.model.TransactionDetails;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerITCase {
    
    @Inject
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGet() {
        final ResponseEntity<TransactionDetails> entity = restTemplate.getForEntity("/transaction/foobar", TransactionDetails.class);
        
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }
    
}
