package com.airportmanagment.service;

import com.airportmanagment.model.Airline;
import com.airportmanagment.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {
    
    @Autowired
    private AirlineRepository airlineRepository;
    
    public int create(Airline airline) {
        return airlineRepository.create(airline);
    }
    
    public List<Airline> findAll() {
        return airlineRepository.findAll();
    }
    
    public Airline findById(Long id) {
        return airlineRepository.findById(id);
    }
    
    public int update(Airline airline) {
        return airlineRepository.update(airline);
    }
    
    public int delete(Long id) {
        return airlineRepository.delete(id);
    }
    
    public Airline findByName(String name) {
        return airlineRepository.findByName(name);
    }
}