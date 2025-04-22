package com.airportmanagment.service;

import com.airportmanagment.model.Terminal;
import com.airportmanagment.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {
    
    @Autowired
    private TerminalRepository terminalRepository;
    
    public int create(Terminal terminal) {
        return terminalRepository.create(terminal);
    }
    
    public List<Terminal> findAll() {
        return terminalRepository.findAll();
    }
    
    public Terminal findById(Long id) {
        return terminalRepository.findById(id);
    }
    
    public int update(Terminal terminal) {
        return terminalRepository.update(terminal);
    }
    
    public int delete(Long id) {
        return terminalRepository.delete(id);
    }
    
    public Terminal findByName(String name) {
        return terminalRepository.findByName(name);
    }
}
