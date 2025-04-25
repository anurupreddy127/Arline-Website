package com.airportmanagment.model;

import java.time.LocalDateTime;

public class Flight {
    private Long flightId;
    private Long originAirportId;
    private Long destinationAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
    private Long airlineId;
    private Long aircraftId;
    
    private String originAirportName;
    private String originAirportCode;
    private String destinationAirportName;
    private String destinationAirportCode;
    private String airlineName;
    private String aircraftName;
    private String aircraftType;
    
    public Flight() {
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public Long getOriginAirportId() {
        return originAirportId;
    }
    
    public void setOriginAirportId(Long originAirportId) {
        this.originAirportId = originAirportId;
    }
    
    public Long getDestinationAirportId() {
        return destinationAirportId;
    }
    
    public void setDestinationAirportId(Long destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }
    
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getAirlineId() {
        return airlineId;
    }
    
    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
    
    public Long getAircraftId() {
        return aircraftId;
    }
    
    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }
    
    public String getOriginAirportName() {
        return originAirportName;
    }
    
    public void setOriginAirportName(String originAirportName) {
        this.originAirportName = originAirportName;
    }
    
    public String getOriginAirportCode() {
        return originAirportCode;
    }
    
    public void setOriginAirportCode(String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }
    
    public String getDestinationAirportName() {
        return destinationAirportName;
    }
    
    public void setDestinationAirportName(String destinationAirportName) {
        this.destinationAirportName = destinationAirportName;
    }
    
    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }
    
    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    
    public String getAircraftName() {
        return aircraftName;
    }
    
    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }
    
    public String getAircraftType() {
        return aircraftType;
    }
    
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }
}