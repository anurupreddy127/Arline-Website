package com.airportmanagment.controller;

import com.airportmanagment.model.FlightCrew;
import com.airportmanagment.service.FlightCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-crew")
public class FlightCrewController {
    
    @Autowired
    private FlightCrewService flightCrewService;
    
    @PostMapping
    public ResponseEntity<String> createCrewMember(@RequestBody FlightCrew crew) {
        int result = flightCrewService.create(crew);
        if (result > 0) {
            return new ResponseEntity<>("Flight crew member created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create flight crew member", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<FlightCrew>> getAllCrewMembers() {
        List<FlightCrew> crewList = flightCrewService.findAllWithDetails();
        return new ResponseEntity<>(crewList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FlightCrew> getCrewMemberById(@PathVariable Long id) {
        FlightCrew crew = flightCrewService.findByIdWithDetails(id);
        if (crew != null) {
            return new ResponseEntity<>(crew, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCrewMember(@PathVariable Long id, @RequestBody FlightCrew crew) {
        crew.setCrewId(id);
        int result = flightCrewService.update(crew);
        if (result > 0) {
            return new ResponseEntity<>("Flight crew member updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update flight crew member", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCrewMember(@PathVariable Long id) {
        int result = flightCrewService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Flight crew member deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete flight crew member", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightCrew>> getCrewByFlight(@PathVariable Long flightId) {
        List<FlightCrew> crewList = flightCrewService.findByFlight(flightId);
        return new ResponseEntity<>(crewList, HttpStatus.OK);
    }
    
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FlightCrew>> getCrewByAirline(@PathVariable Long airlineId) {
        List<FlightCrew> crewList = flightCrewService.findByAirline(airlineId);
        return new ResponseEntity<>(crewList, HttpStatus.OK);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<FlightCrew>> getCrewByRole(@PathVariable String role) {
        List<FlightCrew> crewList = flightCrewService.findByRole(role);
        return new ResponseEntity<>(crewList, HttpStatus.OK);
    }
}
