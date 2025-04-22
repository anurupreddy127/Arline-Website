package com.airportmanagment.repository;

import com.airportmanagment.model.Baggage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BaggageRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public BaggageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Baggage> baggageRowMapper = new RowMapper<Baggage>() {
        @Override
        public Baggage mapRow(ResultSet rs, int rowNum) throws SQLException {
            Baggage baggage = new Baggage();
            baggage.setBaggageId(rs.getLong("baggage_id"));
            baggage.setLocation(rs.getString("location"));
            baggage.setStatus(rs.getString("status"));
            baggage.setPassengerId(rs.getLong("passenger_id"));
            baggage.setFlightId(rs.getLong("flight_id"));
            return baggage;
        }
    };
    
    private RowMapper<Baggage> baggageWithDetailsRowMapper = new RowMapper<Baggage>() {
        @Override
        public Baggage mapRow(ResultSet rs, int rowNum) throws SQLException {
            Baggage baggage = new Baggage();
            baggage.setBaggageId(rs.getLong("baggage_id"));
            baggage.setLocation(rs.getString("location"));
            baggage.setStatus(rs.getString("status"));
            baggage.setPassengerId(rs.getLong("passenger_id"));
            baggage.setFlightId(rs.getLong("flight_id"));
            baggage.setPassengerName(rs.getString("passenger_name"));
            baggage.setFlightDetails(rs.getString("flight_details"));
            return baggage;
        }
    };
    
    // CREATE
    public int create(Baggage baggage) {
        String sql = "INSERT INTO baggage (location, status, passenger_id, flight_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, baggage.getLocation(), baggage.getStatus(), 
                                 baggage.getPassengerId(), baggage.getFlightId());
    }
    
    // READ ALL WITH DETAILS
    public List<Baggage> findAllWithDetails() {
        String sql = "SELECT b.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details " +
                    "FROM baggage b " +
                    "LEFT JOIN passengers p ON b.passenger_id = p.passenger_id " +
                    "LEFT JOIN flights f ON b.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id";
        return jdbcTemplate.query(sql, baggageWithDetailsRowMapper);
    }
    
    // READ BY ID WITH DETAILS
    public Baggage findByIdWithDetails(Long baggageId) {
        String sql = "SELECT b.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details " +
                    "FROM baggage b " +
                    "LEFT JOIN passengers p ON b.passenger_id = p.passenger_id " +
                    "LEFT JOIN flights f ON b.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "WHERE b.baggage_id = ?";
        return jdbcTemplate.queryForObject(sql, baggageWithDetailsRowMapper, baggageId);
    }
    
    // UPDATE
    public int update(Baggage baggage) {
        String sql = "UPDATE baggage SET location = ?, status = ?, passenger_id = ?, flight_id = ? WHERE baggage_id = ?";
        return jdbcTemplate.update(sql, baggage.getLocation(), baggage.getStatus(), 
                                 baggage.getPassengerId(), baggage.getFlightId(), baggage.getBaggageId());
    }
    
    // DELETE
    public int delete(Long baggageId) {
        String sql = "DELETE FROM baggage WHERE baggage_id = ?";
        return jdbcTemplate.update(sql, baggageId);
    }
    
    // FIND BY PASSENGER
    public List<Baggage> findByPassenger(Long passengerId) {
        String sql = "SELECT * FROM baggage WHERE passenger_id = ?";
        return jdbcTemplate.query(sql, baggageRowMapper, passengerId);
    }
    
    // FIND BY FLIGHT
    public List<Baggage> findByFlight(Long flightId) {
        String sql = "SELECT * FROM baggage WHERE flight_id = ?";
        return jdbcTemplate.query(sql, baggageRowMapper, flightId);
    }
    
    // FIND BY STATUS
    public List<Baggage> findByStatus(String status) {
        String sql = "SELECT * FROM baggage WHERE status = ?";
        return jdbcTemplate.query(sql, baggageRowMapper, status);
    }
    
    // UPDATE STATUS
    public int updateStatus(Long baggageId, String status) {
        String sql = "UPDATE baggage SET status = ? WHERE baggage_id = ?";
        return jdbcTemplate.update(sql, status, baggageId);
    }
    
    // UPDATE LOCATION
    public int updateLocation(Long baggageId, String location) {
        String sql = "UPDATE baggage SET location = ? WHERE baggage_id = ?";
        return jdbcTemplate.update(sql, location, baggageId);
    }
}
