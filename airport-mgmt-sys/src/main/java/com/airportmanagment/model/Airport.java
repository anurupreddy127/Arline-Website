package com.airportmanagment.model;

public class Airport {
    private Long airportId;
    private String name;
    private String city;
    private String country;
    private String code;
    
    public Airport() {
    }
    
    public Long getAirportId() {
        return airportId;
    }
    
    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}
