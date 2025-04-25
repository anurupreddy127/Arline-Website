package com.airportmanagment.repository;

import com.airportmanagment.model.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Ticket> ticketRowMapper = new RowMapper<Ticket>() {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setTicketId(rs.getLong("ticket_id"));
            ticket.setSeat(rs.getString("seat"));
            ticket.setPrice(rs.getBigDecimal("price"));
            ticket.setBookedStatus(rs.getBoolean("booked_status"));
            ticket.setPassengerId(rs.getLong("passenger_id"));
            ticket.setFlightId(rs.getLong("flight_id"));
            ticket.setClassId(rs.getLong("class_id"));
            return ticket;
        }
    };
    
    private RowMapper<Ticket> ticketWithDetailsRowMapper = new RowMapper<Ticket>() {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setTicketId(rs.getLong("ticket_id"));
            ticket.setSeat(rs.getString("seat"));
            ticket.setPrice(rs.getBigDecimal("price"));
            ticket.setBookedStatus(rs.getBoolean("booked_status"));
            ticket.setPassengerId(rs.getLong("passenger_id"));
            ticket.setFlightId(rs.getLong("flight_id"));
            ticket.setClassId(rs.getLong("class_id"));
            
            // Additional details from joins
            ticket.setPassengerName(rs.getString("passenger_name"));
            ticket.setFlightDetails(rs.getString("flight_details"));
            ticket.setClassName(rs.getString("class_name"));
            
            return ticket;
        }
    };
    
    // CREATE
    public int create(Ticket ticket) {
        String sql = "INSERT INTO tickets (seat, price, booked_status, passenger_id, flight_id, class_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            ticket.getSeat(),
            ticket.getPrice(),
            ticket.getBookedStatus(),
            ticket.getPassengerId(),
            ticket.getFlightId(),
            ticket.getClassId()
        );
    }
    
    // READ ALL WITH DETAILS
    public List<Ticket> findAllWithDetails() {
        String sql = "SELECT t.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details, " +
                    "tc.name as class_name " +
                    "FROM tickets t " +
                    "LEFT JOIN passengers p ON t.passenger_id = p.passenger_id " +
                    "LEFT JOIN flights f ON t.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN ticket_classes tc ON t.class_id = tc.class_id";
        
        return jdbcTemplate.query(sql, ticketWithDetailsRowMapper);
    }
    
    // READ BY ID WITH DETAILS
    public Ticket findByIdWithDetails(Long ticketId) {
        String sql = "SELECT t.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details, " +
                    "tc.name as class_name " +
                    "FROM tickets t " +
                    "LEFT JOIN passengers p ON t.passenger_id = p.passenger_id " +
                    "LEFT JOIN flights f ON t.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN ticket_classes tc ON t.class_id = tc.class_id " +
                    "WHERE t.ticket_id = ?";
        
        return jdbcTemplate.queryForObject(sql, ticketWithDetailsRowMapper, ticketId);
    }

    public List<Ticket> findByPassengerWithDetails(Long passengerId) {
        String sql = "SELECT t.*, " +
                "p.name as passenger_name, " +
                "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details, " +
                "tc.name as class_name " +
                "FROM tickets t " +
                "LEFT JOIN passengers p ON t.passenger_id = p.passenger_id " +
                "LEFT JOIN flights f ON t.flight_id = f.flight_id " +
                "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                "LEFT JOIN ticket_classes tc ON t.class_id = tc.class_id " +
                "WHERE t.passenger_id = ?";
    
        return jdbcTemplate.query(sql, ticketWithDetailsRowMapper, passengerId);
    }
    
    // UPDATE
    public int update(Ticket ticket) {
        String sql = "UPDATE tickets SET seat = ?, price = ?, booked_status = ?, " +
                    "passenger_id = ?, flight_id = ?, class_id = ? WHERE ticket_id = ?";
        return jdbcTemplate.update(sql, 
            ticket.getSeat(),
            ticket.getPrice(),
            ticket.getBookedStatus(),
            ticket.getPassengerId(),
            ticket.getFlightId(),
            ticket.getClassId(),
            ticket.getTicketId()
        );
    }
    
    // DELETE
    public int delete(Long ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        return jdbcTemplate.update(sql, ticketId);
    }
    
    // FIND BY PASSENGER
    public List<Ticket> findByPassenger(Long passengerId) {
        String sql = "SELECT * FROM tickets WHERE passenger_id = ?";
        return jdbcTemplate.query(sql, ticketRowMapper, passengerId);
    }
    
    // FIND BY FLIGHT
    public List<Ticket> findByFlight(Long flightId) {
        String sql = "SELECT * FROM tickets WHERE flight_id = ?";
        return jdbcTemplate.query(sql, ticketRowMapper, flightId);
    }
    
    // FIND AVAILABLE TICKETS BY FLIGHT
    public List<Ticket> findAvailableByFlight(Long flightId) {
        String sql = "SELECT * FROM tickets WHERE flight_id = ? AND booked_status = false";
        return jdbcTemplate.query(sql, ticketRowMapper, flightId);
    }
    
    // BOOK TICKET
    public int bookTicket(Long ticketId, Long passengerId) {
        String sql = "UPDATE tickets SET booked_status = true, passenger_id = ? WHERE ticket_id = ? AND booked_status = false";
        return jdbcTemplate.update(sql, passengerId, ticketId);
    }
}