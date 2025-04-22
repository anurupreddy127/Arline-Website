package com.airportmanagment.model;

public class FlightCrew {
    private Long crewId;
    private String name;
    private String role;
    private Long flightId;
    private Long airlineId;
    
    // For joined data
    private String flightDetails;
    private String airlineName;
    
    public FlightCrew() {
    }
    
    public Long getCrewId() {
        return crewId;
    }
    
    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public Long getAirlineId() {
        return airlineId;
    }
    
    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
    
    public String getFlightDetails() {
        return flightDetails;
    }
    
    public void setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
