
package com.airportmanagment.repository;

import com.airportmanagment.model.Aircraft;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AircraftRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AircraftRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Aircraft> aircraftRowMapper = new RowMapper<Aircraft>() {
        @Override
        public Aircraft mapRow(ResultSet rs, int rowNum) throws SQLException {
            Aircraft aircraft = new Aircraft();
            aircraft.setAircraftId(rs.getLong("aircraft_id"));
            aircraft.setName(rs.getString("name"));
            aircraft.setType(rs.getString("type"));
            aircraft.setCapacity(rs.getInt("capacity"));
            aircraft.setAirlineId(rs.getLong("airline_id"));
            return aircraft;
        }
    };
    
    private RowMapper<Aircraft> aircraftWithDetailsRowMapper = new RowMapper<Aircraft>() {
        @Override
        public Aircraft mapRow(ResultSet rs, int rowNum) throws SQLException {
            Aircraft aircraft = new Aircraft();
            aircraft.setAircraftId(rs.getLong("aircraft_id"));
            aircraft.setName(rs.getString("name"));
            aircraft.setType(rs.getString("type"));
            aircraft.setCapacity(rs.getInt("capacity"));
            aircraft.setAirlineId(rs.getLong("airline_id"));
            aircraft.setAirlineName(rs.getString("airline_name"));
            return aircraft;
        }
    };

    public int create(Aircraft aircraft) {
        String sql = "INSERT INTO aircraft (name, type, capacity, airline_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, aircraft.getName(), aircraft.getType(), aircraft.getCapacity(), aircraft.getAirlineId());
    }

    public List<Aircraft> findAllWithDetails() {
        String sql = "SELECT a.*, al.name as airline_name " +
                    "FROM aircraft a " +
                    "LEFT JOIN airlines al ON a.airline_id = al.airline_id";
        return jdbcTemplate.query(sql, aircraftWithDetailsRowMapper);
    }

    public Aircraft findByIdWithDetails(Long aircraftId) {
        String sql = "SELECT a.*, al.name as airline_name " +
                    "FROM aircraft a " +
                    "LEFT JOIN airlines al ON a.airline_id = al.airline_id " +
                    "WHERE a.aircraft_id = ?";
        return jdbcTemplate.queryForObject(sql, aircraftWithDetailsRowMapper, aircraftId);
    }

    public int update(Aircraft aircraft) {
        String sql = "UPDATE aircraft SET name = ?, type = ?, capacity = ?, airline_id = ? WHERE aircraft_id = ?";
        return jdbcTemplate.update(sql, aircraft.getName(), aircraft.getType(), aircraft.getCapacity(), aircraft.getAirlineId(), aircraft.getAircraftId());
    }

    public int delete(Long aircraftId) {
        String sql = "DELETE FROM aircraft WHERE aircraft_id = ?";
        return jdbcTemplate.update(sql, aircraftId);
    }

    public List<Aircraft> findByAirline(Long airlineId) {
        String sql = "SELECT * FROM aircraft WHERE airline_id = ?";
        return jdbcTemplate.query(sql, aircraftRowMapper, airlineId);
    }

    public List<Aircraft> findByType(String type) {
        String sql = "SELECT * FROM aircraft WHERE type = ?";
        return jdbcTemplate.query(sql, aircraftRowMapper, type);
    }
}