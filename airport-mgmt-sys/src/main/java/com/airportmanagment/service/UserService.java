package com.airportmanagment.service;

import com.airportmanagment.model.User;
import com.airportmanagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public int create(User user) {
        return userRepository.create(user);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public int update(User user) {
        return userRepository.update(user);
    }
    
    public int delete(Long id) {
        return userRepository.delete(id);
    }
    
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }
}
