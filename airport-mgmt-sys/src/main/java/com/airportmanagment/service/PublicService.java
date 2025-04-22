package com.airportmanagment.service;

import com.airportmanagment.model.Flight;
import com.airportmanagment.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAllWithDetails();
    }

    public List<Flight> searchFlights(Long originAirportId, Long destinationAirportId) {
        return flightRepository.findByRoute(originAirportId, destinationAirportId);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findByIdWithDetails(id);
    }
}
