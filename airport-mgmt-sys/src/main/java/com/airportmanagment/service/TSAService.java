package com.airportmanagment.service;

import com.airportmanagment.model.TSA;
import com.airportmanagment.repository.TSARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TSAService {
    
    @Autowired
    private TSARepository tsaRepository;
    
    public int create(TSA tsa) {
        return tsaRepository.create(tsa);
    }
    
    public List<TSA> findAllWithDetails() {
        return tsaRepository.findAllWithDetails();
    }
    
    public TSA findByIdWithDetails(Long id) {
        return tsaRepository.findByIdWithDetails(id);
    }
    
    public int update(TSA tsa) {
        return tsaRepository.update(tsa);
    }
    
    public int delete(Long id) {
        return tsaRepository.delete(id);
    }
    
    public List<TSA> findByTerminal(Long terminalId) {
        return tsaRepository.findByTerminal(terminalId);
    }
    
    public List<TSA> findByClearanceLevel(String clearanceLevel) {
        return tsaRepository.findByClearanceLevel(clearanceLevel);
    }
    
    public List<TSA> findByCheckpoint(Integer checkpointNumber) {
        return tsaRepository.findByCheckpoint(checkpointNumber);
    }
}