package com.airportmanagment.repository;

import com.airportmanagment.model.Payments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PaymentsRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PaymentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Payments> paymentsRowMapper = new RowMapper<Payments>() {
        @Override
        public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payments payment = new Payments();
            payment.setPaymentId(rs.getLong("payment_id"));
            payment.setDate(rs.getDate("date").toLocalDate());
            payment.setPaidStatus(rs.getBoolean("paid_status"));
            payment.setTicketId(rs.getLong("ticket_id"));
            return payment;
        }
    };
    
    private RowMapper<Payments> paymentsWithDetailsRowMapper = new RowMapper<Payments>() {
        @Override
        public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payments payment = new Payments();
            payment.setPaymentId(rs.getLong("payment_id"));
            payment.setDate(rs.getDate("date").toLocalDate());
            payment.setPaidStatus(rs.getBoolean("paid_status"));
            payment.setTicketId(rs.getLong("ticket_id"));
            payment.setTicketDetails(rs.getString("ticket_details"));
            payment.setPassengerName(rs.getString("passenger_name"));
            payment.setTicketPrice(rs.getDouble("ticket_price"));
            return payment;
        }
    };

    public int create(Payments payment) {
        String sql = "INSERT INTO payments (date, paid_status, ticket_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, payment.getDate(), payment.getPaidStatus(), payment.getTicketId());
    }

    public List<Payments> findAllWithDetails() {
        String sql = "SELECT pay.*, " +
                    "CONCAT(t.seat, ' - Flight ', f.flight_id) as ticket_details, " +
                    "p.name as passenger_name, " +
                    "t.price as ticket_price " +
                    "FROM payments pay " +
                    "LEFT JOIN tickets t ON pay.ticket_id = t.ticket_id " +
                    "LEFT JOIN flights f ON t.flight_id = f.flight_id " +
                    "LEFT JOIN passengers p ON t.passenger_id = p.passenger_id";
        return jdbcTemplate.query(sql, paymentsWithDetailsRowMapper);
    }

    public Payments findByIdWithDetails(Long paymentId) {
        String sql = "SELECT pay.*, " +
                    "CONCAT(t.seat, ' - Flight ', f.flight_id) as ticket_details, " +
                    "p.name as passenger_name, " +
                    "t.price as ticket_price " +
                    "FROM payments pay " +
                    "LEFT JOIN tickets t ON pay.ticket_id = t.ticket_id " +
                    "LEFT JOIN flights f ON t.flight_id = f.flight_id " +
                    "LEFT JOIN passengers p ON t.passenger_id = p.passenger_id " +
                    "WHERE pay.payment_id = ?";
        return jdbcTemplate.queryForObject(sql, paymentsWithDetailsRowMapper, paymentId);
    }

    public int update(Payments payment) {
        String sql = "UPDATE payments SET date = ?, paid_status = ?, ticket_id = ? WHERE payment_id = ?";
        return jdbcTemplate.update(sql, payment.getDate(), payment.getPaidStatus(), payment.getTicketId(), payment.getPaymentId());
    }

    public int delete(Long paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        return jdbcTemplate.update(sql, paymentId);
    }

    public Payments findByTicket(Long ticketId) {
        String sql = "SELECT * FROM payments WHERE ticket_id = ?";
        return jdbcTemplate.queryForObject(sql, paymentsRowMapper, ticketId);
    }

    public List<Payments> findByPaidStatus(Boolean paidStatus) {
        String sql = "SELECT * FROM payments WHERE paid_status = ?";
        return jdbcTemplate.query(sql, paymentsRowMapper, paidStatus);
    }

    public List<Payments> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM payments WHERE date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, paymentsRowMapper, startDate, endDate);
    }

    public int updatePaidStatus(Long paymentId, Boolean paidStatus) {
        String sql = "UPDATE payments SET paid_status = ? WHERE payment_id = ?";
        return jdbcTemplate.update(sql, paidStatus, paymentId);
    }

    public Double getTotalRevenue() {
        String sql = "SELECT SUM(t.price) FROM payments p " +
                    "JOIN tickets t ON p.ticket_id = t.ticket_id " +
                    "WHERE p.paid_status = true";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SUM(t.price) FROM payments p " +
                    "JOIN tickets t ON p.ticket_id = t.ticket_id " +
                    "WHERE p.paid_status = true AND p.date BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, Double.class, startDate, endDate);
    }
}
