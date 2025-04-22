package com.airportmanagment.controller;

import com.airportmanagment.model.Dependents;
import com.airportmanagment.service.DependentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dependents")
public class DependentsController {
    
    @Autowired
    private DependentsService dependentsService;
    
    @PostMapping
    public ResponseEntity<String> createDependent(@RequestBody Dependents dependent) {
        int result = dependentsService.create(dependent);
        if (result > 0) {
            return new ResponseEntity<>("Dependent created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create dependent", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Dependents>> getAllDependents() {
        List<Dependents> dependents = dependentsService.findAllWithDetails();
        return new ResponseEntity<>(dependents, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Dependents> getDependentById(@PathVariable Long id) {
        Dependents dependent = dependentsService.findByIdWithDetails(id);
        if (dependent != null) {
            return new ResponseEntity<>(dependent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDependent(@PathVariable Long id, @RequestBody Dependents dependent) {
        dependent.setDependentId(id);
        int result = dependentsService.update(dependent);
        if (result > 0) {
            return new ResponseEntity<>("Dependent updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update dependent", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDependent(@PathVariable Long id) {
        int result = dependentsService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Dependent deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete dependent", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Dependents>> getDependentsByPassenger(@PathVariable Long passengerId) {
        List<Dependents> dependents = dependentsService.findByPassenger(passengerId);
        return new ResponseEntity<>(dependents, HttpStatus.OK);
    }
    
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Dependents> getDependentByTicket(@PathVariable Long ticketId) {
        Dependents dependent = dependentsService.findByTicket(ticketId);
        if (dependent != null) {
            return new ResponseEntity<>(dependent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/loyalty/{loyaltyId}")
    public ResponseEntity<List<Dependents>> getDependentsByLoyaltyId(@PathVariable String loyaltyId) {
        List<Dependents> dependents = dependentsService.findByLoyaltyId(loyaltyId);
        return new ResponseEntity<>(dependents, HttpStatus.OK);
    }
}
