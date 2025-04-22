package com.airportmanagment.model;

public class Terminal {
    private Long terminalId;
    private String name;
    private String location;
    
    public Terminal() {
    }
    
    public Long getTerminalId() {
        return terminalId;
    }
    
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}
