package com.airportmanagment.repository;

import com.airportmanagment.model.FlightCrew;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FlightCrewRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public FlightCrewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<FlightCrew> crewRowMapper = new RowMapper<FlightCrew>() {
        @Override
        public FlightCrew mapRow(ResultSet rs, int rowNum) throws SQLException {
            FlightCrew crew = new FlightCrew();
            crew.setCrewId(rs.getLong("crew_id"));
            crew.setName(rs.getString("name"));
            crew.setRole(rs.getString("role"));
            crew.setFlightId(rs.getLong("flight_id"));
            crew.setAirlineId(rs.getLong("airline_id"));
            return crew;
        }
    };
    
    private RowMapper<FlightCrew> crewWithDetailsRowMapper = new RowMapper<FlightCrew>() {
        @Override
        public FlightCrew mapRow(ResultSet rs, int rowNum) throws SQLException {
            FlightCrew crew = new FlightCrew();
            crew.setCrewId(rs.getLong("crew_id"));
            crew.setName(rs.getString("name"));
            crew.setRole(rs.getString("role"));
            crew.setFlightId(rs.getLong("flight_id"));
            crew.setAirlineId(rs.getLong("airline_id"));
            crew.setFlightDetails(rs.getString("flight_details"));
            crew.setAirlineName(rs.getString("airline_name"));
            return crew;
        }
    };

    public int create(FlightCrew crew) {
        String sql = "INSERT INTO flight_crew (name, role, flight_id, airline_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, crew.getName(), crew.getRole(), crew.getFlightId(), crew.getAirlineId());
    }

    public List<FlightCrew> findAllWithDetails() {
        String sql = "SELECT fc.*, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details, " +
                    "al.name as airline_name " +
                    "FROM flight_crew fc " +
                    "LEFT JOIN flights f ON fc.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN airlines al ON fc.airline_id = al.airline_id";
        return jdbcTemplate.query(sql, crewWithDetailsRowMapper);
    }

    public FlightCrew findByIdWithDetails(Long crewId) {
        String sql = "SELECT fc.*, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details, " +
                    "al.name as airline_name " +
                    "FROM flight_crew fc " +
                    "LEFT JOIN flights f ON fc.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN airlines al ON fc.airline_id = al.airline_id " +
                    "WHERE fc.crew_id = ?";
        return jdbcTemplate.queryForObject(sql, crewWithDetailsRowMapper, crewId);
    }

    public int update(FlightCrew crew) {
        String sql = "UPDATE flight_crew SET name = ?, role = ?, flight_id = ?, airline_id = ? WHERE crew_id = ?";
        return jdbcTemplate.update(sql, crew.getName(), crew.getRole(), crew.getFlightId(), crew.getAirlineId(), crew.getCrewId());
    }

    public int delete(Long crewId) {
        String sql = "DELETE FROM flight_crew WHERE crew_id = ?";
        return jdbcTemplate.update(sql, crewId);
    }

    public List<FlightCrew> findByFlight(Long flightId) {
        String sql = "SELECT * FROM flight_crew WHERE flight_id = ?";
        return jdbcTemplate.query(sql, crewRowMapper, flightId);
    }

    public List<FlightCrew> findByAirline(Long airlineId) {
        String sql = "SELECT * FROM flight_crew WHERE airline_id = ?";
        return jdbcTemplate.query(sql, crewRowMapper, airlineId);
    }

    public List<FlightCrew> findByRole(String role) {
        String sql = "SELECT * FROM flight_crew WHERE role = ?";
        return jdbcTemplate.query(sql, crewRowMapper, role);
    }
}
