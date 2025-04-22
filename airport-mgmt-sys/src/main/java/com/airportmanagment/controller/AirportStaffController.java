package com.airportmanagment.controller;

import com.airportmanagment.model.AirportStaff;
import com.airportmanagment.service.AirportStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airport-staff")
public class AirportStaffController {
    
    @Autowired
    private AirportStaffService airportStaffService;
    
    @PostMapping
    public ResponseEntity<String> createStaff(@RequestBody AirportStaff staff) {
        int result = airportStaffService.create(staff);
        if (result > 0) {
            return new ResponseEntity<>("Airport staff created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create airport staff", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<AirportStaff>> getAllStaff() {
        List<AirportStaff> staffList = airportStaffService.findAllWithDetails();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AirportStaff> getStaffById(@PathVariable Long id) {
        AirportStaff staff = airportStaffService.findByIdWithDetails(id);
        if (staff != null) {
            return new ResponseEntity<>(staff, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id, @RequestBody AirportStaff staff) {
        staff.setStaffId(id);
        int result = airportStaffService.update(staff);
        if (result > 0) {
            return new ResponseEntity<>("Airport staff updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update airport staff", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        int result = airportStaffService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Airport staff deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete airport staff", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/terminal/{terminalId}")
    public ResponseEntity<List<AirportStaff>> getStaffByTerminal(@PathVariable Long terminalId) {
        List<AirportStaff> staffList = airportStaffService.findByTerminal(terminalId);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<AirportStaff>> getStaffByRole(@PathVariable String role) {
        List<AirportStaff> staffList = airportStaffService.findByRole(role);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }
} 

