package com.airportmanagment.controller;

import com.airportmanagment.model.TicketClass;
import com.airportmanagment.service.TicketClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-classes")
public class TicketClassController {
    
    @Autowired
    private TicketClassService ticketClassService;
    
    @PostMapping
    public ResponseEntity<String> createTicketClass(@RequestBody TicketClass ticketClass) {
        int result = ticketClassService.create(ticketClass);
        if (result > 0) {
            return new ResponseEntity<>("Ticket class created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create ticket class", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<TicketClass>> getAllTicketClasses() {
        List<TicketClass> ticketClasses = ticketClassService.findAll();
        return new ResponseEntity<>(ticketClasses, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TicketClass> getTicketClassById(@PathVariable Long id) {
        TicketClass ticketClass = ticketClassService.findById(id);
        if (ticketClass != null) {
            return new ResponseEntity<>(ticketClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<TicketClass> getTicketClassByName(@PathVariable String name) {
        TicketClass ticketClass = ticketClassService.findByName(name);
        if (ticketClass != null) {
            return new ResponseEntity<>(ticketClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicketClass(@PathVariable Long id, @RequestBody TicketClass ticketClass) {
        ticketClass.setClassId(id);
        int result = ticketClassService.update(ticketClass);
        if (result > 0) {
            return new ResponseEntity<>("Ticket class updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update ticket class", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicketClass(@PathVariable Long id) {
        int result = ticketClassService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Ticket class deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete ticket class", HttpStatus.BAD_REQUEST);
    }
}