package com.airportmanagment.service;

import com.airportmanagment.model.Passenger;
import com.airportmanagment.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    public int create(Passenger passenger) {
        return passengerRepository.create(passenger);
    }
    
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }
    
    public Passenger findById(Long id) {
        return passengerRepository.findById(id);
    }
    
    public int update(Passenger passenger) {
        return passengerRepository.update(passenger);
    }
    
    public int delete(Long id) {
        return passengerRepository.delete(id);
    }
    
    public List<Passenger> findByLoyaltyId(String loyaltyId) {
        return passengerRepository.findByLoyaltyId(loyaltyId);
    }
}