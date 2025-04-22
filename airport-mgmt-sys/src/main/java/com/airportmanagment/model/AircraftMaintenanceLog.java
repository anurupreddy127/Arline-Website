package com.airportmanagment.model;

import java.time.LocalDate;

public class AircraftMaintenanceLog {
    private Long logId;
    private Long aircraftId;
    private Long flightId;
    private LocalDate maintenanceDate;
    private String status;
    
    // For joined data
    private String aircraftName;
    private String flightDetails;
    
    public AircraftMaintenanceLog() {
    }
    
    public Long getLogId() {
        return logId;
    }
    
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    
    public Long getAircraftId() {
        return aircraftId;
    }
    
    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }
    
    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAircraftName() {
        return aircraftName;
    }
    
    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }
    
    public String getFlightDetails() {
        return flightDetails;
    }
    
    public void setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
    }
}
