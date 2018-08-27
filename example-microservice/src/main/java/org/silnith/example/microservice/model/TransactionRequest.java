package org.silnith.example.microservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class TransactionRequest {
    
    private String domain;
    
    private String region;
    
    private int count;
    
    public TransactionRequest() {
        super();
    }
    
    public TransactionRequest(@NotNull final String domain, @NotNull final String region, @Min(0) final int count) {
        this();
        this.domain = domain;
        this.region = region;
        this.count = count;
    }
    
    public void setDomain(@NotNull final String domain) {
        this.domain = domain;
    }
    
    public void setRegion(@NotNull final String region) {
        this.region = region;
    }
    
    public void setCount(@Min(0) final int count) {
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
