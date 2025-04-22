package com.airportmanagment.repository;

import com.airportmanagment.model.Airline;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AirlineRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AirlineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Airline> airlineRowMapper = new RowMapper<Airline>() {
        @Override
        public Airline mapRow(ResultSet rs, int rowNum) throws SQLException {
            Airline airline = new Airline();
            airline.setAirlineId(rs.getLong("airline_id"));
            airline.setName(rs.getString("name"));
            return airline;
        }
    };
    
    // CREATE
    public int create(Airline airline) {
        String sql = "INSERT INTO airlines (name) VALUES (?)";
        return jdbcTemplate.update(sql, airline.getName());
    }
    
    // READ ALL
    public List<Airline> findAll() {
        String sql = "SELECT * FROM airlines";
        return jdbcTemplate.query(sql, airlineRowMapper);
    }
    
    // READ BY ID
    public Airline findById(Long airlineId) {
        String sql = "SELECT * FROM airlines WHERE airline_id = ?";
        return jdbcTemplate.queryForObject(sql, airlineRowMapper, airlineId);
    }
    
    // UPDATE
    public int update(Airline airline) {
        String sql = "UPDATE airlines SET name = ? WHERE airline_id = ?";
        return jdbcTemplate.update(sql, airline.getName(), airline.getAirlineId());
    }
    
    // DELETE
    public int delete(Long airlineId) {
        String sql = "DELETE FROM airlines WHERE airline_id = ?";
        return jdbcTemplate.update(sql, airlineId);
    }
    
    // FIND BY NAME
    public Airline findByName(String name) {
        String sql = "SELECT * FROM airlines WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, airlineRowMapper, name);
    }
}