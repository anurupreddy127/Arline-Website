package com.airportmanagment.service;

import com.airportmanagment.model.Payments;
import com.airportmanagment.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentsService {
    
    @Autowired
    private PaymentsRepository paymentsRepository;
    
    public int create(Payments payment) {
        return paymentsRepository.create(payment);
    }
    
    public List<Payments> findAllWithDetails() {
        return paymentsRepository.findAllWithDetails();
    }
    
    public Payments findByIdWithDetails(Long id) {
        return paymentsRepository.findByIdWithDetails(id);
    }
    
    public int update(Payments payment) {
        return paymentsRepository.update(payment);
    }
    
    public int delete(Long id) {
        return paymentsRepository.delete(id);
    }
    
    public Payments findByTicket(Long ticketId) {
        return paymentsRepository.findByTicket(ticketId);
    }
    
    public List<Payments> findByPaidStatus(Boolean paidStatus) {
        return paymentsRepository.findByPaidStatus(paidStatus);
    }
    
    public List<Payments> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentsRepository.findByDateRange(startDate, endDate);
    }
    
    public int updatePaidStatus(Long paymentId, Boolean paidStatus) {
        return paymentsRepository.updatePaidStatus(paymentId, paidStatus);
    }
    
    public Double getTotalRevenue() {
        return paymentsRepository.getTotalRevenue();
    }
    
    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentsRepository.getRevenueByDateRange(startDate, endDate);
    }
}
