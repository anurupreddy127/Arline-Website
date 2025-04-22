package com.airportmanagment.controller;

import com.airportmanagment.model.Airport;
import com.airportmanagment.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    
    @Autowired
    private AirportService airportService;
    
    @PostMapping
    public ResponseEntity<String> createAirport(@RequestBody Airport airport) {
        int result = airportService.create(airport);
        if (result > 0) {
            return new ResponseEntity<>("Airport created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create airport", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.findAll();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Airport airport = airportService.findById(id);
        if (airport != null) {
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Airport> getAirportByCode(@PathVariable String code) {
        Airport airport = airportService.findByCode(code);
        if (airport != null) {
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        airport.setAirportId(id);
        int result = airportService.update(airport);
        if (result > 0) {
            return new ResponseEntity<>("Airport updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update airport", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
        int result = airportService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Airport deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete airport", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Airport>> getAirportsByCity(@PathVariable String city) {
        List<Airport> airports = airportService.findByCity(city);
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Airport>> getAirportsByCountry(@PathVariable String country) {
        List<Airport> airports = airportService.findByCountry(country);
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }
}
