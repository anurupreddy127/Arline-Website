package com.airportmanagment.controller;

import com.airportmanagment.model.Aircraft;
import com.airportmanagment.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {
    
    @Autowired
    private AircraftService aircraftService;
    
    @PostMapping
    public ResponseEntity<String> createAircraft(@RequestBody Aircraft aircraft) {
        int result = aircraftService.create(aircraft);
        if (result > 0) {
            return new ResponseEntity<>("Aircraft created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create aircraft", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircraft() {
        List<Aircraft> aircraft = aircraftService.findAllWithDetails();
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        Aircraft aircraft = aircraftService.findByIdWithDetails(id);
        if (aircraft != null) {
            return new ResponseEntity<>(aircraft, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        aircraft.setAircraftId(id);
        int result = aircraftService.update(aircraft);
        if (result > 0) {
            return new ResponseEntity<>("Aircraft updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update aircraft", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAircraft(@PathVariable Long id) {
        int result = aircraftService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Aircraft deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete aircraft", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<Aircraft>> getAircraftByAirline(@PathVariable Long airlineId) {
        List<Aircraft> aircraft = aircraftService.findByAirline(airlineId);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Aircraft>> getAircraftByType(@PathVariable String type) {
        List<Aircraft> aircraft = aircraftService.findByType(type);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }
}
