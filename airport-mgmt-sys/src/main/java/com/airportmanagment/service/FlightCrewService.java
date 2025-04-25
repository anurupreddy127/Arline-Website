package com.airportmanagment.service;

import com.airportmanagment.model.FlightCrew;
import com.airportmanagment.repository.FlightCrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// 

@Service
public class FlightCrewService {
    
    @Autowired
    private FlightCrewRepository flightCrewRepository;
    
    public int create(FlightCrew crew) {
        return flightCrewRepository.create(crew);
    }
    
    public List<FlightCrew> findAllWithDetails() {
        return flightCrewRepository.findAllWithDetails();
    }
    
    public FlightCrew findByIdWithDetails(Long id) {
        return flightCrewRepository.findByIdWithDetails(id);
    }
    
    public int update(FlightCrew crew) {
        return flightCrewRepository.update(crew);
    }
    
    public int delete(Long id) {
        return flightCrewRepository.delete(id);
    }
    
    public List<FlightCrew> findByFlight(Long flightId) {
        return flightCrewRepository.findByFlight(flightId);
    }
    
    public List<FlightCrew> findByAirline(Long airlineId) {
        return flightCrewRepository.findByAirline(airlineId);
    }
    
    public List<FlightCrew> findByRole(String role) {
        return flightCrewRepository.findByRole(role);
    }
}
