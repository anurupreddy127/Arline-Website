package com.airportmanagment.repository;

import com.airportmanagment.model.Passenger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PassengerRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public PassengerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Passenger> passengerRowMapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet rs, int rowNum) throws SQLException {
            Passenger passenger = new Passenger();
            passenger.setPassengerId(rs.getLong("passenger_id"));
            passenger.setName(rs.getString("name"));
            passenger.setPhone(rs.getString("phone"));
            passenger.setLoyaltyId(rs.getString("loyalty_id"));
            passenger.setUserId(rs.getLong("user_id"));
            return passenger;
        }
    };
    
    // CREATE
    public int create(Passenger passenger) {
        String sql = "INSERT INTO passengers (name, phone, loyalty_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, passenger.getName(), passenger.getPhone(), passenger.getLoyaltyId());
    }

    // CREATE WITH USER ID
    public int createWithUserId(String name, Long userId) {
        String sql = "INSERT INTO passengers (name, user_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, name, userId);
    }
    
    // READ ALL
    public List<Passenger> findAll() {
        String sql = "SELECT * FROM passengers";
        return jdbcTemplate.query(sql, passengerRowMapper);
    }
    
    // READ BY ID
    public Passenger findById(Long id) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        return jdbcTemplate.queryForObject(sql, passengerRowMapper, id);
    }

    // READ BY USER ID
    public Passenger findByUserId(Long userId) {
        String sql = "SELECT * FROM passengers WHERE user_id = ?";
        List<Passenger> passengers = jdbcTemplate.query(sql, passengerRowMapper, userId);
        return passengers.isEmpty() ? null : passengers.get(0);
    }
    
    // UPDATE
    public int update(Passenger passenger) {
        String sql = "UPDATE passengers SET name = ?, phone = ?, loyalty_id = ? WHERE passenger_id = ?";
        return jdbcTemplate.update(sql, passenger.getName(), passenger.getPhone(), 
                                 passenger.getLoyaltyId(), passenger.getPassengerId());
    }
    
    // DELETE
    public int delete(Long id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    // FIND BY LOYALTY ID
    public List<Passenger> findByLoyaltyId(String loyaltyId) {
        String sql = "SELECT * FROM passengers WHERE loyalty_id = ?";
        return jdbcTemplate.query(sql, passengerRowMapper, loyaltyId);
    }
}