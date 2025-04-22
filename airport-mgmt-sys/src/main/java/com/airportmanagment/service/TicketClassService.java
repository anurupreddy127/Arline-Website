package com.airportmanagment.service;

import com.airportmanagment.model.TicketClass;
import com.airportmanagment.repository.TicketClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketClassService {
    
    @Autowired
    private TicketClassRepository ticketClassRepository;
    
    public int create(TicketClass ticketClass) {
        return ticketClassRepository.create(ticketClass);
    }
    
    public List<TicketClass> findAll() {
        return ticketClassRepository.findAll();
    }
    
    public TicketClass findById(Long id) {
        return ticketClassRepository.findById(id);
    }
    
    public TicketClass findByName(String name) {
        return ticketClassRepository.findByName(name);
    }
    
    public int update(TicketClass ticketClass) {
        return ticketClassRepository.update(ticketClass);
    }
    
    public int delete(Long id) {
        return ticketClassRepository.delete(id);
    }
}
