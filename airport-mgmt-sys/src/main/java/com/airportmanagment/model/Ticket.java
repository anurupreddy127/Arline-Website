package com.airportmanagment.model;

import java.math.BigDecimal;

public class Ticket {
    private Long ticketId;
    private String seat;
    private BigDecimal price;
    private Boolean bookedStatus;
    private Long passengerId;
    private Long flightId;
    private Long classId;  // This is the correct field name
    
    // Additional fields for joined data
    private String passengerName;
    private String flightDetails;
    private String className;
    
    public Ticket() {
    }
    
    public Long getTicketId() {
        return ticketId;
    }
    
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    public String getSeat() {
        return seat;
    }
    
    public void setSeat(String seat) {
        this.seat = seat;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getBookedStatus() {
        return bookedStatus;
    }
    
    public void setBookedStatus(Boolean bookedStatus) {
        this.bookedStatus = bookedStatus;
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
    
    public Long getClassId() {
        return classId;
    }
    
    public void setClassId(Long classId) {
        this.classId = classId;
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
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
}
