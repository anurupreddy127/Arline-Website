package com.airportmanagment.model;

import java.time.LocalDate;

public class Payments {
    private Long paymentId;
    private LocalDate date;
    private Boolean paidStatus;
    private Long ticketId;
    
    private String ticketDetails;
    private String passengerName;
    private Double ticketPrice;
    
    public Payments() {
    }
    
    public Long getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Boolean getPaidStatus() {
        return paidStatus;
    }
    
    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }
    
    public Long getTicketId() {
        return ticketId;
    }
    
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    public String getTicketDetails() {
        return ticketDetails;
    }
    
    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public Double getTicketPrice() {
        return ticketPrice;
    }
    
    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}