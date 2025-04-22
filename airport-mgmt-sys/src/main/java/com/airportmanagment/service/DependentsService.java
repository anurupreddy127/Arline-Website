package com.airportmanagment.service;

import com.airportmanagment.model.Dependents;
import com.airportmanagment.repository.DependentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependentsService {
    
    @Autowired
    private DependentsRepository dependentsRepository;
    
    public int create(Dependents dependent) {
        return dependentsRepository.create(dependent);
    }
    
    public List<Dependents> findAllWithDetails() {
        return dependentsRepository.findAllWithDetails();
    }
    
    public Dependents findByIdWithDetails(Long id) {
        return dependentsRepository.findByIdWithDetails(id);
    }
    
    public int update(Dependents dependent) {
        return dependentsRepository.update(dependent);
    }
    
    public int delete(Long id) {
        return dependentsRepository.delete(id);
    }
    
    public List<Dependents> findByPassenger(Long passengerId) {
        return dependentsRepository.findByPassenger(passengerId);
    }
    
    public Dependents findByTicket(Long ticketId) {
        return dependentsRepository.findByTicket(ticketId);
    }
    
    public List<Dependents> findByLoyaltyId(String loyaltyId) {
        return dependentsRepository.findByLoyaltyId(loyaltyId);
    }
}
