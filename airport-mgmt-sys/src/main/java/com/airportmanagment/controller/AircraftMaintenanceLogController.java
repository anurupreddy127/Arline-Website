package com.airportmanagment.controller;

import com.airportmanagment.model.AircraftMaintenanceLog;
import com.airportmanagment.service.AircraftMaintenanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft-maintenance-logs")
public class AircraftMaintenanceLogController {
    
    @Autowired
    private AircraftMaintenanceLogService maintenanceLogService;
    
    @PostMapping
    public ResponseEntity<String> createMaintenanceLog(@RequestBody AircraftMaintenanceLog log) {
        int result = maintenanceLogService.create(log);
        if (result > 0) {
            return new ResponseEntity<>("Maintenance log created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create maintenance log", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<AircraftMaintenanceLog>> getAllMaintenanceLogs() {
        List<AircraftMaintenanceLog> logs = maintenanceLogService.findAllWithDetails();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AircraftMaintenanceLog> getMaintenanceLogById(@PathVariable Long id) {
        AircraftMaintenanceLog log = maintenanceLogService.findByIdWithDetails(id);
        if (log != null) {
            return new ResponseEntity<>(log, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMaintenanceLog(@PathVariable Long id, @RequestBody AircraftMaintenanceLog log) {
        log.setLogId(id);
        int result = maintenanceLogService.update(log);
        if (result > 0) {
            return new ResponseEntity<>("Maintenance log updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update maintenance log", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenanceLog(@PathVariable Long id) {
        int result = maintenanceLogService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Maintenance log deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete maintenance log", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<AircraftMaintenanceLog>> getMaintenanceLogsByAircraft(@PathVariable Long aircraftId) {
        List<AircraftMaintenanceLog> logs = maintenanceLogService.findByAircraft(aircraftId);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AircraftMaintenanceLog>> getMaintenanceLogsByStatus(@PathVariable String status) {
        List<AircraftMaintenanceLog> logs = maintenanceLogService.findByStatus(status);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<AircraftMaintenanceLog>> getMaintenanceLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AircraftMaintenanceLog> logs = maintenanceLogService.findByDateRange(startDate, endDate);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
