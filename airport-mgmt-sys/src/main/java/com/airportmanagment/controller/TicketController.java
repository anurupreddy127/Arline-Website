package com.airportmanagment.controller;

import com.airportmanagment.model.Ticket;
import com.airportmanagment.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody Ticket ticket) {
        int result = ticketService.create(ticket);
        if (result > 0) {
            return new ResponseEntity<>("Ticket created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create ticket", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllWithDetails();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.findByIdWithDetails(id);
        if (ticket != null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        ticket.setTicketId(id);
        int result = ticketService.update(ticket);
        if (result > 0) {
            return new ResponseEntity<>("Ticket updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update ticket", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        int result = ticketService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Ticket deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete ticket", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Ticket>> getTicketsByPassenger(@PathVariable Long passengerId) {
        List<Ticket> tickets = ticketService.findByPassenger(passengerId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}/detailed")
public ResponseEntity<List<Ticket>> getTicketsByPassengerWithDetails(@PathVariable Long passengerId) {
    List<Ticket> tickets = ticketService.findByPassengerWithDetails(passengerId);
    return new ResponseEntity<>(tickets, HttpStatus.OK);
}
    
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Ticket>> getTicketsByFlight(@PathVariable Long flightId) {
        List<Ticket> tickets = ticketService.findByFlight(flightId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    
    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<Ticket>> getAvailableTicketsByFlight(@PathVariable Long flightId) {
        List<Ticket> tickets = ticketService.findAvailableByFlight(flightId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PutMapping("/{ticketId}/price")
public ResponseEntity<String> updateTicketPrice(
    @PathVariable Long ticketId,
    @RequestParam BigDecimal newPrice) {
    
    int result = ticketService.updateTicketPrice(ticketId, newPrice);
    if (result > 0) {
        return new ResponseEntity<>("Ticket price updated successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Failed to update ticket price", HttpStatus.BAD_REQUEST);
}
    
    @PostMapping("/{ticketId}/book")
public ResponseEntity<String> bookTicket(
    @PathVariable Long ticketId,
    @RequestParam Long passengerId,
    @RequestParam Long classId) {

    int result = ticketService.bookTicket(ticketId, passengerId, classId);
    if (result > 0) {
        return new ResponseEntity<>("Ticket booked successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Failed to book ticket", HttpStatus.BAD_REQUEST);
}
}
