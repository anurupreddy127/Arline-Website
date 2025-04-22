package com.airportmanagment.model;

public class Lounge {
    private Long loungeId;
    private String location;
    private String loyaltyId;
    private Long terminalId;
    
    // For joined data
    private String terminalName;
    
    public Lounge() {
    }
    
    public Long getLoungeId() {
        return loungeId;
    }
    
    public void setLoungeId(Long loungeId) {
        this.loungeId = loungeId;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLoyaltyId() {
        return loyaltyId;
    }
    
    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }
    
    public Long getTerminalId() {
        return terminalId;
    }
    
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }
    
    public String getTerminalName() {
        return terminalName;
    }
    
    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }
}