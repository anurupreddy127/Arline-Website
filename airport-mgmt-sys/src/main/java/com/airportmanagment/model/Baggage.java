package com.airportmanagment.model;

public class Baggage {
    private Long baggageId;
    private String location;
    private String status;
    private Long passengerId;
    private Long flightId;
    
    private String passengerName;
    private String flightDetails;
    
    public Baggage() {
    }
    
    public Long getBaggageId() {
        return baggageId;
    }
    
    public void setBaggageId(Long baggageId) {
        this.baggageId = baggageId;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getFlightDetails() {
        return flightDetails;
    }
    
    public void setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
    }
}