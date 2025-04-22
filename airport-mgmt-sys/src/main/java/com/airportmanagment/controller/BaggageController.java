package com.airportmanagment.controller;


import com.airportmanagment.model.Baggage;
import com.airportmanagment.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baggage")
public class BaggageController {
    
    @Autowired
    private BaggageService baggageService;
    
    @PostMapping
    public ResponseEntity<String> createBaggage(@RequestBody Baggage baggage) {
        int result = baggageService.create(baggage);
        if (result > 0) {
            return new ResponseEntity<>("Baggage created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create baggage", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Baggage>> getAllBaggage() {
        List<Baggage> baggageList = baggageService.findAllWithDetails();
        return new ResponseEntity<>(baggageList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Baggage> getBaggageById(@PathVariable Long id) {
        Baggage baggage = baggageService.findByIdWithDetails(id);
        if (baggage != null) {
            return new ResponseEntity<>(baggage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBaggage(@PathVariable Long id, @RequestBody Baggage baggage) {
        baggage.setBaggageId(id);
        int result = baggageService.update(baggage);
        if (result > 0) {
            return new ResponseEntity<>("Baggage updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update baggage", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBaggage(@PathVariable Long id) {
        int result = baggageService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Baggage deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete baggage", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Baggage>> getBaggageByPassenger(@PathVariable Long passengerId) {
        List<Baggage> baggageList = baggageService.findByPassenger(passengerId);
        return new ResponseEntity<>(baggageList, HttpStatus.OK);
    }
    
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Baggage>> getBaggageByFlight(@PathVariable Long flightId) {
        List<Baggage> baggageList = baggageService.findByFlight(flightId);
        return new ResponseEntity<>(baggageList, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Baggage>> getBaggageByStatus(@PathVariable String status) {
        List<Baggage> baggageList = baggageService.findByStatus(status);
        return new ResponseEntity<>(baggageList, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateBaggageStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        int result = baggageService.updateStatus(id, status);
        if (result > 0) {
            return new ResponseEntity<>("Baggage status updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update baggage status", HttpStatus.BAD_REQUEST);
    }
    
    @PatchMapping("/{id}/location")
    public ResponseEntity<String> updateBaggageLocation(
            @PathVariable Long id,
            @RequestParam String location) {
        int result = baggageService.updateLocation(id, location);
        if (result > 0) {
            return new ResponseEntity<>("Baggage location updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update baggage location", HttpStatus.BAD_REQUEST);
    }
}