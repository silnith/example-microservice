package org.silnith.example.microservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransactionRequest {
    
    private final String domain;
    
    private final String region;
    
    private final int count;
    
    public TransactionRequest(@NotNull final String domain, @NotNull final String region, @Min(0) final int count) {
        super();
        this.domain = domain;
        this.region = region;
        this.count = count;
    }
    
    @NotNull
    public String getDomain() {
        return domain;
    }

    @NotNull
    public String getRegion() {
        return region;
    }
    
    @Min(0)
    public int getCount() {
        return count;
    }

}
