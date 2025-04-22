package com.airportmanagment.service;

import com.airportmanagment.model.AirportStaff;
import com.airportmanagment.repository.AirportStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportStaffService {
    
    @Autowired
    private AirportStaffRepository airportStaffRepository;
    
    public int create(AirportStaff staff) {
        return airportStaffRepository.create(staff);
    }
    
    public List<AirportStaff> findAllWithDetails() {
        return airportStaffRepository.findAllWithDetails();
    }
    
    public AirportStaff findByIdWithDetails(Long id) {
        return airportStaffRepository.findByIdWithDetails(id);
    }
    
    public int update(AirportStaff staff) {
        return airportStaffRepository.update(staff);
    }
    
    public int delete(Long id) {
        return airportStaffRepository.delete(id);
    }
    
    public List<AirportStaff> findByTerminal(Long terminalId) {
        return airportStaffRepository.findByTerminal(terminalId);
    }
    
    public List<AirportStaff> findByRole(String role) {
        return airportStaffRepository.findByRole(role);
    }
}
