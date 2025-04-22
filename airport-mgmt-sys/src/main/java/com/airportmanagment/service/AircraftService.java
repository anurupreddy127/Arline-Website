package com.airportmanagment.service;

import com.airportmanagment.model.Aircraft;
import com.airportmanagment.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService {
    
    @Autowired
    private AircraftRepository aircraftRepository;
    
    public int create(Aircraft aircraft) {
        return aircraftRepository.create(aircraft);
    }
    
    public List<Aircraft> findAllWithDetails() {
        return aircraftRepository.findAllWithDetails();
    }
    
    public Aircraft findByIdWithDetails(Long id) {
        return aircraftRepository.findByIdWithDetails(id);
    }
    
    public int update(Aircraft aircraft) {
        return aircraftRepository.update(aircraft);
    }
    
    public int delete(Long id) {
        return aircraftRepository.delete(id);
    }
    
    public List<Aircraft> findByAirline(Long airlineId) {
        return aircraftRepository.findByAirline(airlineId);
    }
    
    public List<Aircraft> findByType(String type) {
        return aircraftRepository.findByType(type);
    }
}
