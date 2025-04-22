package com.airportmanagment.repository;

import com.airportmanagment.model.Airport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AirportRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AirportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Airport> airportRowMapper = new RowMapper<Airport>() {
        @Override
        public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
            Airport airport = new Airport();
            airport.setAirportId(rs.getLong("airport_id"));
            airport.setName(rs.getString("name"));
            airport.setCity(rs.getString("city"));
            airport.setCountry(rs.getString("country"));
            airport.setCode(rs.getString("code"));
            return airport;
        }
    };
    
    // CREATE
    public int create(Airport airport) {
        String sql = "INSERT INTO airports (name, city, country, code) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, airport.getName(), airport.getCity(), 
                                 airport.getCountry(), airport.getCode());
    }
    
    // READ ALL
    public List<Airport> findAll() {
        String sql = "SELECT * FROM airports";
        return jdbcTemplate.query(sql, airportRowMapper);
    }
    
    // READ BY ID
    public Airport findById(Long airportId) {
        String sql = "SELECT * FROM airports WHERE airport_id = ?";
        return jdbcTemplate.queryForObject(sql, airportRowMapper, airportId);
    }
    
    // READ BY CODE
    public Airport findByCode(String code) {
        String sql = "SELECT * FROM airports WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, airportRowMapper, code);
    }
    
    // UPDATE
    public int update(Airport airport) {
        String sql = "UPDATE airports SET name = ?, city = ?, country = ?, code = ? WHERE airport_id = ?";
        return jdbcTemplate.update(sql, airport.getName(), airport.getCity(), 
                                 airport.getCountry(), airport.getCode(), airport.getAirportId());
    }
    
    // DELETE
    public int delete(Long airportId) {
        String sql = "DELETE FROM airports WHERE airport_id = ?";
        return jdbcTemplate.update(sql, airportId);
    }
    
    // FIND BY CITY
    public List<Airport> findByCity(String city) {
        String sql = "SELECT * FROM airports WHERE city = ?";
        return jdbcTemplate.query(sql, airportRowMapper, city);
    }
    
    // FIND BY COUNTRY
    public List<Airport> findByCountry(String country) {
        String sql = "SELECT * FROM airports WHERE country = ?";
        return jdbcTemplate.query(sql, airportRowMapper, country);
    }
}