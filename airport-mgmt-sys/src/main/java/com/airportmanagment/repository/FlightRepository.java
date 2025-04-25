package com.airportmanagment.repository;

import com.airportmanagment.model.Flight;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FlightRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public FlightRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Flight> flightRowMapper = new RowMapper<Flight>() {
        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flight flight = new Flight();
            flight.setFlightId(rs.getLong("flight_id"));
            flight.setOriginAirportId(rs.getLong("origin_airport_id"));
            flight.setDestinationAirportId(rs.getLong("destination_airport_id"));
            flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
            flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
            flight.setStatus(rs.getString("status"));
            flight.setAirlineId(rs.getLong("airline_id"));
            flight.setAircraftId(rs.getLong("aircraft_id"));
            return flight;
        }
    };
    
    private RowMapper<Flight> flightWithDetailsRowMapper = new RowMapper<Flight>() {
        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flight flight = new Flight();
            flight.setFlightId(rs.getLong("flight_id"));
            flight.setOriginAirportId(rs.getLong("origin_airport_id"));
            flight.setDestinationAirportId(rs.getLong("destination_airport_id"));
            flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
            flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
            flight.setStatus(rs.getString("status"));
            flight.setAirlineId(rs.getLong("airline_id"));
            flight.setAircraftId(rs.getLong("aircraft_id"));
            
            // Additional details from joins
            flight.setOriginAirportName(rs.getString("origin_name"));
            flight.setOriginAirportCode(rs.getString("origin_code"));
            flight.setDestinationAirportName(rs.getString("dest_name"));
            flight.setDestinationAirportCode(rs.getString("dest_code"));
            flight.setAirlineName(rs.getString("airline_name"));
            flight.setAircraftName(rs.getString("aircraft_name"));
            flight.setAircraftType(rs.getString("aircraft_type"));
            
            return flight;
        }
    };

    public List<Flight> findByRouteWithDetails(Long originAirportId, Long destinationAirportId) {
        String sql = "SELECT f.*, " +
                "origin.name as origin_name, origin.code as origin_code, " +
                "dest.name as dest_name, dest.code as dest_code, " +
                "al.name as airline_name, " +
                "ac.name as aircraft_name, ac.type as aircraft_type " +
                "FROM flights f " +
                "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                "LEFT JOIN airlines al ON f.airline_id = al.airline_id " +
                "LEFT JOIN aircraft ac ON f.aircraft_id = ac.aircraft_id " +
                "WHERE f.origin_airport_id = ? AND f.destination_airport_id = ?";
        
        return jdbcTemplate.query(sql, flightWithDetailsRowMapper, originAirportId, destinationAirportId);
    }
    
    // CREATE

    public int create(Flight flight) {
        String sql = "INSERT INTO flights (origin_airport_id, destination_airport_id, departure_time, " +
                    "arrival_time, status, airline_id, aircraft_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            flight.getOriginAirportId(),
            flight.getDestinationAirportId(),
            flight.getDepartureTime(),
            flight.getArrivalTime(),
            flight.getStatus(),
            flight.getAirlineId(),
            flight.getAircraftId()
        );
    }

    public List<Flight> findAllWithDetails() {
        String sql = "SELECT f.*, " +
                    "origin.name as origin_name, origin.code as origin_code, " +
                    "dest.name as dest_name, dest.code as dest_code, " +
                    "al.name as airline_name, " +
                    "ac.name as aircraft_name, ac.type as aircraft_type " +
                    "FROM flights f " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN airlines al ON f.airline_id = al.airline_id " +
                    "LEFT JOIN aircraft ac ON f.aircraft_id = ac.aircraft_id";
        
        return jdbcTemplate.query(sql, flightWithDetailsRowMapper);
    }

    public Flight findByIdWithDetails(Long flightId) {
        String sql = "SELECT f.*, " +
                    "origin.name as origin_name, origin.code as origin_code, " +
                    "dest.name as dest_name, dest.code as dest_code, " +
                    "al.name as airline_name, " +
                    "ac.name as aircraft_name, ac.type as aircraft_type " +
                    "FROM flights f " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "LEFT JOIN airlines al ON f.airline_id = al.airline_id " +
                    "LEFT JOIN aircraft ac ON f.aircraft_id = ac.aircraft_id " +
                    "WHERE f.flight_id = ?";
        
        return jdbcTemplate.queryForObject(sql, flightWithDetailsRowMapper, flightId);
    }

    public int update(Flight flight) {
        String sql = "UPDATE flights SET origin_airport_id = ?, destination_airport_id = ?, " +
                    "departure_time = ?, arrival_time = ?, status = ?, airline_id = ?, " +
                    "aircraft_id = ? WHERE flight_id = ?";
        return jdbcTemplate.update(sql, 
            flight.getOriginAirportId(),
            flight.getDestinationAirportId(),
            flight.getDepartureTime(),
            flight.getArrivalTime(),
            flight.getStatus(),
            flight.getAirlineId(),
            flight.getAircraftId(),
            flight.getFlightId()
        );
    }

    public int delete(Long flightId) {
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        return jdbcTemplate.update(sql, flightId);
    }

    public List<Flight> findByStatus(String status) {
        String sql = "SELECT * FROM flights WHERE status = ?";
        return jdbcTemplate.query(sql, flightRowMapper, status);
    }

    public List<Flight> findByAirline(Long airlineId) {
        String sql = "SELECT * FROM flights WHERE airline_id = ?";
        return jdbcTemplate.query(sql, flightRowMapper, airlineId);
    }

    public List<Flight> findByRoute(Long originAirportId, Long destinationAirportId) {
        String sql = "SELECT * FROM flights WHERE origin_airport_id = ? AND destination_airport_id = ?";
        return jdbcTemplate.query(sql, flightRowMapper, originAirportId, destinationAirportId);
    }

    public List<Flight> findByDepartureTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT * FROM flights WHERE departure_time BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, flightRowMapper, startTime, endTime);
    }

    public List<Flight> findFlightsDepartingToday() {
        String sql = "SELECT * FROM flights WHERE DATE(departure_time) = CURDATE()";
        return jdbcTemplate.query(sql, flightRowMapper);
    }

    public int updateStatus(Long flightId, String status) {
        String sql = "UPDATE flights SET status = ? WHERE flight_id = ?";
        return jdbcTemplate.update(sql, status, flightId);
    }

    public List<Flight> findDelayedFlights() {
        String sql = "SELECT * FROM flights WHERE status = 'DELAYED'";
        return jdbcTemplate.query(sql, flightRowMapper);
    }
}