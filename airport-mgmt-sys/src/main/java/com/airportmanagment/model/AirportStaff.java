package com.airportmanagment.model;

public class AirportStaff {
    private Long staffId;
    private String name;
    private String role;
    private Long terminalId;
    
    // For joined data
    private String terminalName;
    
    public AirportStaff() {
    }
    
    public Long getStaffId() {
        return staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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
    
    public Long getTerminalId() {
        return terminalId;
    }
    
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }
    
    public String getTerminalName() {
        return terminalName;
    }
    
    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }
}