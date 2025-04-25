package com.airportmanagment.service;

import com.airportmanagment.model.Flight;
import com.airportmanagment.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    public int create(Flight flight) {
        return flightRepository.create(flight);
    }
    
    public List<Flight> findAllWithDetails() {
        return flightRepository.findAllWithDetails();
    }

    public List<Flight> findByRouteWithDetails(Long originAirportId, Long destinationAirportId) {
        return flightRepository.findByRouteWithDetails(originAirportId, destinationAirportId);
    }
    
    public Flight findByIdWithDetails(Long id) {
        return flightRepository.findByIdWithDetails(id);
    }
    
    public int update(Flight flight) {
        return flightRepository.update(flight);
    }
    
    public int delete(Long id) {
        return flightRepository.delete(id);
    }
    
    public List<Flight> findByStatus(String status) {
        return flightRepository.findByStatus(status);
    }
    
    public List<Flight> findByAirline(Long airlineId) {
        return flightRepository.findByAirline(airlineId);
    }
    
    public List<Flight> findByRoute(Long originAirportId, Long destinationAirportId) {
        return flightRepository.findByRoute(originAirportId, destinationAirportId);
    }
    
    public List<Flight> findByDepartureTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return flightRepository.findByDepartureTimeRange(startTime, endTime);
    }
    
    public List<Flight> findFlightsDepartingToday() {
        return flightRepository.findFlightsDepartingToday();
    }
    
    public int updateStatus(Long flightId, String status) {
        return flightRepository.updateStatus(flightId, status);
    }
    
    public List<Flight> findDelayedFlights() {
        return flightRepository.findDelayedFlights();
    }
}