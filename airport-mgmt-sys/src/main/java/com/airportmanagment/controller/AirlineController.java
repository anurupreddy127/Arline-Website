package com.airportmanagment.controller;

import com.airportmanagment.model.Airline;
import com.airportmanagment.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
    
    @Autowired
    private AirlineService airlineService;
    
    @PostMapping
    public ResponseEntity<String> createAirline(@RequestBody Airline airline) {
        int result = airlineService.create(airline);
        if (result > 0) {
            return new ResponseEntity<>("Airline created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create airline", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Airline>> getAllAirlines() {
        List<Airline> airlines = airlineService.findAll();
        return new ResponseEntity<>(airlines, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Long id) {
        Airline airline = airlineService.findById(id);
        if (airline != null) {
            return new ResponseEntity<>(airline, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAirline(@PathVariable Long id, @RequestBody Airline airline) {
        airline.setAirlineId(id);
        int result = airlineService.update(airline);
        if (result > 0) {
            return new ResponseEntity<>("Airline updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update airline", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable Long id) {
        int result = airlineService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Airline deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete airline", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Airline> getAirlineByName(@PathVariable String name) {
        Airline airline = airlineService.findByName(name);
        if (airline != null) {
            return new ResponseEntity<>(airline, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}