package com.airportmanagment.controller;

import com.airportmanagment.model.Terminal;
import com.airportmanagment.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminals")
public class TerminalController {
    
    @Autowired
    private TerminalService terminalService;
    
    @PostMapping
    public ResponseEntity<String> createTerminal(@RequestBody Terminal terminal) {
        int result = terminalService.create(terminal);
        if (result > 0) {
            return new ResponseEntity<>("Terminal created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create terminal", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Terminal>> getAllTerminals() {
        List<Terminal> terminals = terminalService.findAll();
        return new ResponseEntity<>(terminals, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Terminal> getTerminalById(@PathVariable Long id) {
        Terminal terminal = terminalService.findById(id);
        if (terminal != null) {
            return new ResponseEntity<>(terminal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTerminal(@PathVariable Long id, @RequestBody Terminal terminal) {
        terminal.setTerminalId(id);
        int result = terminalService.update(terminal);
        if (result > 0) {
            return new ResponseEntity<>("Terminal updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update terminal", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTerminal(@PathVariable Long id) {
        int result = terminalService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Terminal deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete terminal", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Terminal> getTerminalByName(@PathVariable String name) {
        Terminal terminal = terminalService.findByName(name);
        if (terminal != null) {
            return new ResponseEntity<>(terminal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
