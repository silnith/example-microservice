package org.silnith.example.microservice.jaxrs;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.sql.SQLException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.silnith.example.microservice.data.DataProvider;
import org.silnith.example.microservice.model.TransactionDetails;
import org.silnith.example.microservice.model.TransactionRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerITCase {
    
    @Inject
    private TestRestTemplate restTemplate;
    
    @Inject
    private DataProvider dataProvider;
    
    @Test
    public void testCreateTransaction() {
        final String orderId = "testCreateTransaction";
        final String domain = "eureka";
        final String region = "sadness";
        final int count = 20;
        
        final TransactionRequest order = new TransactionRequest(domain, region, count);
        final RequestEntity<TransactionRequest> httpEntity = RequestEntity
                .put(URI.create("/api/transaction/" + orderId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(order);
        
        final ResponseEntity<TransactionDetails> responseEntity = restTemplate.exchange(httpEntity, TransactionDetails.class);
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        final TransactionDetails body = responseEntity.getBody();
        assertEquals(orderId, body.getId());
        assertEquals(domain, body.getDomain());
        assertEquals(region, body.getRegion());
        assertEquals(count, body.getCount());
        assertNotNull(body.getCreated());
    }
    
    @Test
    public void testCreateTransaction_Serialization() {
        final String orderId = "testCreateTransaction_Serialization";
        final String order = "{"
                + "\"domain\": \"eureka\","
                + "\"region\": \"sadness\","
                + "\"count\": 20"
                + "}";
        final RequestEntity<String> httpEntity = RequestEntity
                .put(URI.create("/api/transaction/" + orderId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(order);
        
        final ResponseEntity<String> responseEntity = restTemplate.exchange(httpEntity, String.class);
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        final String body = responseEntity.getBody();
//        assertEquals("{}", body);
    }
    
    @Test
    public void testCreateTransaction_Duplicate() throws SQLException {
        final String orderId = "testCreateTransaction_Duplicate";
        final String domain = "eureka";
        final String region = "sadness";
        final int count = 20;
        
        dataProvider.createTransaction(orderId, domain, region, count);
        
        final TransactionRequest order = new TransactionRequest(domain, region, count);
        final RequestEntity<TransactionRequest> httpEntity = RequestEntity
                .put(URI.create("/api/transaction/" + orderId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(order);
        
        final ResponseEntity<TransactionDetails> responseEntity = restTemplate.exchange(httpEntity, TransactionDetails.class);
        
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testCreateTransaction_Conflict() throws SQLException {
        final String orderId = "testCreateTransaction_Conflict";
        final String domain = "eureka";
        final String region = "sadness";
        final int count = 20;
        
        dataProvider.createTransaction(orderId, "europa", "sin", 5);
        
        final TransactionRequest order = new TransactionRequest(domain, region, count);
        final RequestEntity<TransactionRequest> httpEntity = RequestEntity
                .put(URI.create("/api/transaction/" + orderId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(order);
        
        final ResponseEntity<String> responseEntity = restTemplate.exchange(httpEntity, String.class);
        
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
    
}
