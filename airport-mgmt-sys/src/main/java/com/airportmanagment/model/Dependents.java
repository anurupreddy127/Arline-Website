package com.airportmanagment.model;

public class Dependents {
    private Long dependentId;
    private String name;
    private String phone;
    private String loyaltyId;
    private Long passengerId;
    private Long ticketId;
    
    // For joined data
    private String passengerName;
    private String ticketDetails;
    
    public Dependents() {
    }
    
    public Long getDependentId() {
        return dependentId;
    }
    
    public void setDependentId(Long dependentId) {
        this.dependentId = dependentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLoyaltyId() {
        return loyaltyId;
    }
    
    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }
    
    public Long getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
    
    public Long getTicketId() {
        return ticketId;
    }
    
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getTicketDetails() {
        return ticketDetails;
    }
    
    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
}