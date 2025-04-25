package com.airportmanagment.controller;

import com.airportmanagment.model.Passenger;
import com.airportmanagment.model.User;
import com.airportmanagment.service.PassengerService;
import com.airportmanagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
private PassengerService passengerService;
    
    @PostMapping
public ResponseEntity<String> createUser(@RequestBody User user) {
    int result = userService.create(user);
    if (result > 0) {
        // If user is a passenger, add a corresponding passenger
        if ("PASSENGER".equalsIgnoreCase(user.getRole())) {
            User createdUser = userService.findByUsername(user.getUsername());
            if (createdUser != null) {
                // Inject PassengerRepository via service layer
                Passenger passenger = new Passenger();
                passenger.setName(user.getUsername()); // Just using username for now
                passenger.setUserId(createdUser.getId());
        
                // Debug print to check execution
                System.out.println("Creating passenger for userId: " + passenger.getUserId() + ", name: " + passenger.getName());
        
                // Create passenger (call service layer to create)
                passengerService.createWithUserId(passenger.getName(), passenger.getUserId());
            }
        }
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
    return new ResponseEntity<>("Failed to create user", HttpStatus.BAD_REQUEST);
}
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        int result = userService.update(user);
        if (result > 0) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update user", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        int result = userService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete user", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        List<User> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}