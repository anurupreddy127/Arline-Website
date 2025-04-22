package com.airportmanagment.service;

import com.airportmanagment.model.Lounge;
import com.airportmanagment.repository.LoungeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoungeService {
    
    @Autowired
    private LoungeRepository loungeRepository;
    
    public int create(Lounge lounge) {
        return loungeRepository.create(lounge);
    }
    
    public List<Lounge> findAllWithDetails() {
        return loungeRepository.findAllWithDetails();
    }
    
    public Lounge findByIdWithDetails(Long id) {
        return loungeRepository.findByIdWithDetails(id);
    }
    
    public int update(Lounge lounge) {
        return loungeRepository.update(lounge);
    }
    
    public int delete(Long id) {
        return loungeRepository.delete(id);
    }
    
    public List<Lounge> findByTerminal(Long terminalId) {
        return loungeRepository.findByTerminal(terminalId);
    }
    
    public List<Lounge> findByLoyaltyId(String loyaltyId) {
        return loungeRepository.findByLoyaltyId(loyaltyId);
    }
}
