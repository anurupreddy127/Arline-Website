package com.airportmanagment.controller;

import com.airportmanagment.model.TSA;
import com.airportmanagment.service.TSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tsa")
public class TSAController {
    
    @Autowired
    private TSAService tsaService;
    
    @PostMapping
    public ResponseEntity<String> createTSA(@RequestBody TSA tsa) {
        int result = tsaService.create(tsa);
        if (result > 0) {
            return new ResponseEntity<>("TSA personnel created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create TSA personnel", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<TSA>> getAllTSA() {
        List<TSA> tsaList = tsaService.findAllWithDetails();
        return new ResponseEntity<>(tsaList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TSA> getTSAById(@PathVariable Long id) {
        TSA tsa = tsaService.findByIdWithDetails(id);
        if (tsa != null) {
            return new ResponseEntity<>(tsa, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTSA(@PathVariable Long id, @RequestBody TSA tsa) {
        tsa.setPersonalId(id);
        int result = tsaService.update(tsa);
        if (result > 0) {
            return new ResponseEntity<>("TSA personnel updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update TSA personnel", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTSA(@PathVariable Long id) {
        int result = tsaService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("TSA personnel deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete TSA personnel", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/terminal/{terminalId}")
    public ResponseEntity<List<TSA>> getTSAByTerminal(@PathVariable Long terminalId) {
        List<TSA> tsaList = tsaService.findByTerminal(terminalId);
        return new ResponseEntity<>(tsaList, HttpStatus.OK);
    }
    
    @GetMapping("/clearance/{clearanceLevel}")
    public ResponseEntity<List<TSA>> getTSAByClearanceLevel(@PathVariable String clearanceLevel) {
        List<TSA> tsaList = tsaService.findByClearanceLevel(clearanceLevel);
        return new ResponseEntity<>(tsaList, HttpStatus.OK);
    }
    
    @GetMapping("/checkpoint/{checkpointNumber}")
    public ResponseEntity<List<TSA>> getTSAByCheckpoint(@PathVariable Integer checkpointNumber) {
        List<TSA> tsaList = tsaService.findByCheckpoint(checkpointNumber);
        return new ResponseEntity<>(tsaList, HttpStatus.OK);
    }
}