package org.silnith.example.microservice.model;

import java.beans.ConstructorProperties;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A request to create a new transaction.
 */
@Schema(title = "Transaction Request", description = "The property values to use when creating a transaction.")
@XmlRootElement(name = "transactionRequest")
public class TransactionRequest {
    
    @XmlElement(name = "domain")
    @JsonbProperty("domain")
    private String domain;
    
    @XmlElement(name = "region")
    @JsonbProperty("region")
    private String region;
    
    @XmlElement(name = "count", required = true)
    @JsonbProperty("count")
    private int count;
    
    /**
     * Creates a new transaction request.
     * 
     * @see TransactionRequest#TransactionRequest(String, String, int)
     */
    public TransactionRequest() {
        super();
    }
    
    /**
     * Creates a new transaction request.
     * 
     * @param domain the domain
     * @param region the region
     * @param count the count
     */
    @ConstructorProperties({"domain", "region", "count",})
    public TransactionRequest(
            @JsonbProperty("domain") @NotNull final String domain,
            @JsonbProperty("region") @NotNull final String region,
            @JsonbProperty("count") @Min(0) final int count) {
        this();
        this.domain = domain;
        this.region = region;
        this.count = count;
    }
    
    /**
     * Sets the domain.
     * 
     * @param domain the domain
     */
    public void setDomain(@NotNull final String domain) {
        this.domain = domain;
    }
    
    /**
     * Returns the domain.
     * 
     * @return the domain
     */
    @NotNull
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the region.
     * 
     * @param region the region
     */
    public void setRegion(@NotNull final String region) {
        this.region = region;
    }
    
    /**
     * Returns the region.
     * 
     * @return the region
     */
    @NotNull
    public String getRegion() {
        return region;
    }

    /**
     * Sets the count.
     * 
     * @param count the count
     */
    public void setCount(@Min(0) final int count) {
        this.count = count;
    }
    
    /**
     * Returns the count.
     * 
     * @return the count
     */
    @Min(0)
    public int getCount() {
        return count;
    }
    
}
