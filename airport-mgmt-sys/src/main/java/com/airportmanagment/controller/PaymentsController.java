package com.airportmanagment.controller;

import com.airportmanagment.model.Payments;
import com.airportmanagment.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    
    @Autowired
    private PaymentsService paymentsService;
    
    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Payments payment) {
        int result = paymentsService.create(payment);
        if (result > 0) {
            return new ResponseEntity<>("Payment created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create payment", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping
    public ResponseEntity<List<Payments>> getAllPayments() {
        List<Payments> payments = paymentsService.findAllWithDetails();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payments> getPaymentById(@PathVariable Long id) {
        Payments payment = paymentsService.findByIdWithDetails(id);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable Long id, @RequestBody Payments payment) {
        payment.setPaymentId(id);
        int result = paymentsService.update(payment);
        if (result > 0) {
            return new ResponseEntity<>("Payment updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update payment", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        int result = paymentsService.delete(id);
        if (result > 0) {
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete payment", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Payments> getPaymentByTicket(@PathVariable Long ticketId) {
        Payments payment = paymentsService.findByTicket(ticketId);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/status/{paidStatus}")
    public ResponseEntity<List<Payments>> getPaymentsByStatus(@PathVariable Boolean paidStatus) {
        List<Payments> payments = paymentsService.findByPaidStatus(paidStatus);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Payments>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Payments> payments = paymentsService.findByDateRange(startDate, endDate);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam Boolean paidStatus) {
        int result = paymentsService.updatePaidStatus(id, paidStatus);
        if (result > 0) {
            return new ResponseEntity<>("Payment status updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update payment status", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/revenue/total")
    public ResponseEntity<Double> getTotalRevenue() {
        Double totalRevenue = paymentsService.getTotalRevenue();
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }
    
    @GetMapping("/revenue/date-range")
    public ResponseEntity<Double> getRevenueByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Double revenue = paymentsService.getRevenueByDateRange(startDate, endDate);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }
}