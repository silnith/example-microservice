package org.silnith.example.microservice.model;

import java.beans.ConstructorProperties;
import java.time.Instant;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Encapsulates the details of a transaction record.
 */
@Schema(title = "Transaction Details",
        description = "The details of a transaction record.")
@XmlRootElement(name = "transactionDetails")
public class TransactionDetails {
    
    private final String id;
    
    private final String domain;
    
    private final String region;
    
    private final int count;
    
    private final Instant created;
    
    /**
     * Creates a new transaction details object.
     * 
     * @param id the transaction identifier
     * @param domain the domain
     * @param region the region
     * @param count the count
     * @param created the record creation timestamp
     */
    @ConstructorProperties({"id", "domain", "region", "count", "created",})
    public TransactionDetails(
            @JsonbProperty("id") @NotNull final String id,
            @JsonbProperty("domain") @NotNull final String domain,
            @JsonbProperty("region") @NotNull final String region,
            @JsonbProperty("count") @Min(0) final int count,
            @JsonbProperty("created") @NotNull final Instant created) {
        super();
        this.id = id;
        this.domain = domain;
        this.region = region;
        this.count = count;
        this.created = created;
    }
    
    /**
     * Returns the transaction identifier.
     * 
     * @return the transaction identifier
     */
    @XmlElement(name = "id")
    @JsonbProperty("id")
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Returns the domain.
     * 
     * @return the domain
     */
    @XmlElement(name = "domain")
    @JsonbProperty("domain")
    @NotNull
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the region.
     * 
     * @return the region
     */
    @XmlElement(name = "region")
    @JsonbProperty("region")
    @NotNull
    public String getRegion() {
        return region;
    }

    /**
     * Returns the count.
     * 
     * @return the count
     */
    @XmlElement(name = "count", required = true)
    @JsonbProperty("count")
    @Min(0)
    public int getCount() {
        return count;
    }

    /**
     * Returns the timestamp when the transaction was created.
     * 
     * @return the record creation timestamp
     */
    @XmlElement(name = "created")
    @JsonbProperty("created")
    @NotNull
    public Instant getCreated() {
        return created;
    }
    
}
