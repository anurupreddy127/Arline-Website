package com.airportmanagment.service;

import com.airportmanagment.model.AircraftMaintenanceLog;
import com.airportmanagment.repository.AircraftMaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AircraftMaintenanceLogService {
    
    @Autowired
    private AircraftMaintenanceLogRepository maintenanceLogRepository;
    
    public int create(AircraftMaintenanceLog log) {
        return maintenanceLogRepository.create(log);
    }
    
    public List<AircraftMaintenanceLog> findAllWithDetails() {
        return maintenanceLogRepository.findAllWithDetails();
    }
    
    public AircraftMaintenanceLog findByIdWithDetails(Long id) {
        return maintenanceLogRepository.findByIdWithDetails(id);
    }
    
    public int update(AircraftMaintenanceLog log) {
        return maintenanceLogRepository.update(log);
    }
    
    public int delete(Long id) {
        return maintenanceLogRepository.delete(id);
    }
    
    public List<AircraftMaintenanceLog> findByAircraft(Long aircraftId) {
        return maintenanceLogRepository.findByAircraft(aircraftId);
    }
    
    public List<AircraftMaintenanceLog> findByStatus(String status) {
        return maintenanceLogRepository.findByStatus(status);
    }
    
    public List<AircraftMaintenanceLog> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return maintenanceLogRepository.findByDateRange(startDate, endDate);
    }
}
