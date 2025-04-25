package com.airportmanagment.controller;

import com.airportmanagment.model.Flight;
import com.airportmanagment.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @PostMapping
    public ResponseEntity<String> createFlight(@RequestBody Flight flight) {
        int result = flightService.create(flight);
        if (result > 0) {
            return new ResponseEntity<>("Flight created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create flight", HttpStatus.BAD_REQUEST);
    }
    
    // @GetMapping
    // public ResponseEntity<List<Flight>> getAllFlights() {
    //     List<Flight> flights = flightService.findAllWithDetails();
    //     return new ResponseEntity<>(flights, HttpStatus.OK);
    // }
    
     @GetMapping
    public Map<String, Object> getAllFlights() {
        List<Flight> flights = flightService.findAllWithDetails(); //gets list of flights
        flights.forEach(flight -> System.out.println("Flight ID: " + flight.getFlightId()));
        //maps each flight to a clean JSON that react can easily read
        List<Map<String, Object>> flightData = flights.stream().map(flight -> {
            Map<String, Object> map = new HashMap<>();
            map.put("flightId", flight.getFlightId());
            map.put("origin", flight.getOriginAirportName());
            map.put("destination", flight.getDestinationAirportName());
            map.put("departureTime", flight.getDepartureTime());
            map.put("arrivalTime", flight.getArrivalTime());
            map.put("status", flight.getStatus());
    
            Map<String, Object> airline = new HashMap<>();
            airline.put("name", flight.getAirlineName());
            map.put("airline", airline);
    
            Map<String, Object> aircraft = new HashMap<>();
            aircraft.put("name", flight.getAircraftName());
            aircraft.put("type", flight.getAircraftType());
            map.put("aircraft", aircraft);
    
            return map;
        }).collect(Collectors.toList());
    
        Map<String, Object> response = new HashMap<>();
        response.put("flights", flightData);
        return response;
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.findByIdWithDetails(id);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        flight.setFlightId(id);
        int result = flightService.update(flight);
        if (result > 0) {
            return new ResponseEntity<>("Flight updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update flight", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        int result = flightService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Flight deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete flight", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Flight>> getFlightsByStatus(@PathVariable String status) {
        List<Flight> flights = flightService.findByStatus(status);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<Flight>> getFlightsByAirline(@PathVariable Long airlineId) {
        List<Flight> flights = flightService.findByAirline(airlineId);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping("/route")
    public ResponseEntity<List<Map<String, Object>>> getFlightsByRoute(
            @RequestParam Long originAirportId,
            @RequestParam Long destinationAirportId) {
        
        List<Flight> flights = flightService.findByRouteWithDetails(originAirportId, destinationAirportId);
    
        List<Map<String, Object>> flightData = flights.stream().map(flight -> {
            Map<String, Object> map = new HashMap<>();
            map.put("flightId", flight.getFlightId());
            map.put("origin", flight.getOriginAirportName());
            map.put("destination", flight.getDestinationAirportName());
            map.put("departureTime", flight.getDepartureTime());
            map.put("arrivalTime", flight.getArrivalTime());
            map.put("status", flight.getStatus());
    
            Map<String, Object> airline = new HashMap<>();
            airline.put("name", flight.getAirlineName());
            map.put("airline", airline);
    
            Map<String, Object> aircraft = new HashMap<>();
            aircraft.put("name", flight.getAircraftName());
            aircraft.put("type", flight.getAircraftType());
            map.put("aircraft", aircraft);
    
            return map;
        }).collect(Collectors.toList());
    
        return new ResponseEntity<>(flightData, HttpStatus.OK);
    }
    
    @GetMapping("/departures/today")
    public ResponseEntity<List<Flight>> getFlightsDepartingToday() {
        List<Flight> flights = flightService.findFlightsDepartingToday();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping("/departures/range")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<Flight> flights = flightService.findByDepartureTimeRange(startTime, endTime);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateFlightStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        int result = flightService.updateStatus(id, status);
        if (result > 0) {
            return new ResponseEntity<>("Flight status updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update flight status", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/delayed")
    public ResponseEntity<List<Flight>> getDelayedFlights() {
        List<Flight> flights = flightService.findDelayedFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
