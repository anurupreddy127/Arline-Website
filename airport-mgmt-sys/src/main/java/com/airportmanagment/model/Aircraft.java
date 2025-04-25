package com.airportmanagment.model;

public class Aircraft {
    private Long aircraftId;
    private String name;
    private String type;
    private Integer capacity;
    private Long airlineId;
    
    private String airlineName;
    
    public Aircraft() {
    }
    
    public Long getAircraftId() {
        return aircraftId;
    }
    
    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Long getAirlineId() {
        return airlineId;
    }
    
    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
