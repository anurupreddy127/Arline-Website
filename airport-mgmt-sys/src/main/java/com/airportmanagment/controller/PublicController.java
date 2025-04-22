package com.airportmanagment.controller;

import com.airportmanagment.model.Flight;
import com.airportmanagment.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {
    
    @Autowired
    private PublicService publicService;
    
    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = publicService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping("/flights/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam Long originAirportId, 
            @RequestParam Long destinationAirportId) {
        List<Flight> flights = publicService.searchFlights(originAirportId, destinationAirportId);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = publicService.getFlightById(id);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}