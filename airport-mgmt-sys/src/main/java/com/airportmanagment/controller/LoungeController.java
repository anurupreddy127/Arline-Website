package com.airportmanagment.controller;

import com.airportmanagment.model.Lounge;
import com.airportmanagment.service.LoungeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lounges")
public class LoungeController {
    
    @Autowired
    private LoungeService loungeService;
    
    @PostMapping
    public ResponseEntity<String> createLounge(@RequestBody Lounge lounge) {
        int result = loungeService.create(lounge);
        if (result > 0) {
            return new ResponseEntity<>("Lounge created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create lounge", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Lounge>> getAllLounges() {
        List<Lounge> lounges = loungeService.findAllWithDetails();
        return new ResponseEntity<>(lounges, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Lounge> getLoungeById(@PathVariable Long id) {
        Lounge lounge = loungeService.findByIdWithDetails(id);
        if (lounge != null) {
            return new ResponseEntity<>(lounge, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLounge(@PathVariable Long id, @RequestBody Lounge lounge) {
        lounge.setLoungeId(id);
        int result = loungeService.update(lounge);
        if (result > 0) {
            return new ResponseEntity<>("Lounge updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update lounge", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLounge(@PathVariable Long id) {
        int result = loungeService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Lounge deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete lounge", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/terminal/{terminalId}")
    public ResponseEntity<List<Lounge>> getLoungesByTerminal(@PathVariable Long terminalId) {
        List<Lounge> lounges = loungeService.findByTerminal(terminalId);
        return new ResponseEntity<>(lounges, HttpStatus.OK);
    }
    
    @GetMapping("/loyalty/{loyaltyId}")
    public ResponseEntity<List<Lounge>> getLoungesByLoyaltyId(@PathVariable String loyaltyId) {
        List<Lounge> lounges = loungeService.findByLoyaltyId(loyaltyId);
        return new ResponseEntity<>(lounges, HttpStatus.OK);
    }
}
