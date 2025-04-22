package com.airportmanagment.model;

public class Airline {
    private Long airlineId;
    private String name;
    
    public Airline() {
    }
    
    public Long getAirlineId() {
        return airlineId;
    }
    
    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}