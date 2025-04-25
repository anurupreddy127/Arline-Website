package com.airportmanagment.service;

import com.airportmanagment.model.Ticket;
import com.airportmanagment.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    public int create(Ticket ticket) {
        return ticketRepository.create(ticket);
    }
    
    public List<Ticket> findAllWithDetails() {
        return ticketRepository.findAllWithDetails();
    }
    
    public Ticket findByIdWithDetails(Long id) {
        return ticketRepository.findByIdWithDetails(id);
    }
    
    public int update(Ticket ticket) {
        return ticketRepository.update(ticket);
    }
    
    public int delete(Long id) {
        return ticketRepository.delete(id);
    }

    public List<Ticket> findByPassengerWithDetails(Long passengerId) {
        return ticketRepository.findByPassengerWithDetails(passengerId);
    }
    
    public List<Ticket> findByPassenger(Long passengerId) {
        return ticketRepository.findByPassenger(passengerId);
    }
    
    public List<Ticket> findByFlight(Long flightId) {
        return ticketRepository.findByFlight(flightId);
    }
    
    public List<Ticket> findAvailableByFlight(Long flightId) {
        return ticketRepository.findAvailableByFlight(flightId);
    }
    
    public int bookTicket(Long ticketId, Long passengerId) {
        return ticketRepository.bookTicket(ticketId, passengerId);
    }
}