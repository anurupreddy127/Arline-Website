package com.airportmanagment.service;

import com.airportmanagment.model.Baggage;
import com.airportmanagment.repository.BaggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaggageService {
    
    @Autowired
    private BaggageRepository baggageRepository;
    
    public int create(Baggage baggage) {
        return baggageRepository.create(baggage);
    }
    
    public List<Baggage> findAllWithDetails() {
        return baggageRepository.findAllWithDetails();
    }
    
    public Baggage findByIdWithDetails(Long id) {
        return baggageRepository.findByIdWithDetails(id);
    }
    
    public int update(Baggage baggage) {
        return baggageRepository.update(baggage);
    }
    
    public int delete(Long id) {
        return baggageRepository.delete(id);
    }

    public List<Baggage> findByPassengerAndFlight(Long passengerId, Long flightId) {
        return baggageRepository.findByPassengerAndFlight(passengerId, flightId);
    }
    
    public List<Baggage> findByPassenger(Long passengerId) {
        return baggageRepository.findByPassenger(passengerId);
    }
    
    public List<Baggage> findByFlight(Long flightId) {
        return baggageRepository.findByFlight(flightId);
    }
    
    public List<Baggage> findByStatus(String status) {
        return baggageRepository.findByStatus(status);
    }
    
    public int updateStatus(Long baggageId, String status) {
        return baggageRepository.updateStatus(baggageId, status);
    }
    
    public int updateLocation(Long baggageId, String location) {
        return baggageRepository.updateLocation(baggageId, location);
    }
}