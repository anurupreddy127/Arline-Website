package com.airportmanagment.service;

import com.airportmanagment.model.Airport;
import com.airportmanagment.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    
    @Autowired
    private AirportRepository airportRepository;
    
    public int create(Airport airport) {
        return airportRepository.create(airport);
    }
    
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }
    
    public Airport findById(Long id) {
        return airportRepository.findById(id);
    }
    
    public Airport findByCode(String code) {
        return airportRepository.findByCode(code);
    }
    
    public int update(Airport airport) {
        return airportRepository.update(airport);
    }
    
    public int delete(Long id) {
        return airportRepository.delete(id);
    }
    
    public List<Airport> findByCity(String city) {
        return airportRepository.findByCity(city);
    }
    
    public List<Airport> findByCountry(String country) {
        return airportRepository.findByCountry(country);
    }
}

