package com.airportmanagment.model;

public class TSA {
    private Long personalId;
    private String name;
    private String clearanceLevel;
    private Integer checkpointNumber;
    private Long terminalId;
    
    private String terminalName;
    
    public TSA() {
    }
    
    public Long getPersonalId() {
        return personalId;
    }
    
    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getClearanceLevel() {
        return clearanceLevel;
    }
    
    public void setClearanceLevel(String clearanceLevel) {
        this.clearanceLevel = clearanceLevel;
    }
    
    public Integer getCheckpointNumber() {
        return checkpointNumber;
    }
    
    public void setCheckpointNumber(Integer checkpointNumber) {
        this.checkpointNumber = checkpointNumber;
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