package com.airportmanagment.controller;

import com.airportmanagment.model.Passenger;
import com.airportmanagment.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    
    @Autowired
    private PassengerService passengerService;
    
    @PostMapping
    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger) {
        int result = passengerService.create(passenger);
        if (result > 0) {
            return new ResponseEntity<>("Passenger created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create passenger", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.findAll();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.findById(id);
        if (passenger != null) {
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        passenger.setPassengerId(id);
        int result = passengerService.update(passenger);
        if (result > 0) {
            return new ResponseEntity<>("Passenger updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update passenger", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long id) {
        int result = passengerService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Passenger deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete passenger", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/loyalty/{loyaltyId}")
    public ResponseEntity<List<Passenger>> getPassengersByLoyaltyId(@PathVariable String loyaltyId) {
        List<Passenger> passengers = passengerService.findByLoyaltyId(loyaltyId);
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
}
