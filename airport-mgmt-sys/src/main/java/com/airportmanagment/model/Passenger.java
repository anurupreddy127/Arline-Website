package com.airportmanagment.model;

public class Passenger {
    private Long passengerId;
    private String name;
    private String phone;
    private String loyaltyId;
    
    public Passenger() {
    }
    
    public Long getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
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
}