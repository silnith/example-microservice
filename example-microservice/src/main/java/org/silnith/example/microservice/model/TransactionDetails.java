package org.silnith.example.microservice.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;


public class TransactionDetails {
    
    private final String id;
    
    private final String domain;
    
    private final String region;
    
    private final int count;
    
    private final Instant created;
    
    public TransactionDetails(@NotNull final String id, @NotNull final String domain, @NotNull final String region, final int count, @NotNull final Instant created) {
        super();
        this.id = id;
        this.domain = domain;
        this.region = region;
        this.count = count;
        this.created = created;
    }
    
    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getDomain() {
        return domain;
    }

    @NotNull
    public String getRegion() {
        return region;
    }
    
    public int getCount() {
        return count;
    }

    @NotNull
    public Instant getCreated() {
        return created;
    }
    
}
