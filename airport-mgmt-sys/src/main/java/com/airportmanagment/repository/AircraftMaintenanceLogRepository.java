package com.airportmanagment.repository;

import com.airportmanagment.model.AircraftMaintenanceLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AircraftMaintenanceLogRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AircraftMaintenanceLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<AircraftMaintenanceLog> maintenanceLogRowMapper = new RowMapper<AircraftMaintenanceLog>() {
        @Override
        public AircraftMaintenanceLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            AircraftMaintenanceLog log = new AircraftMaintenanceLog();
            log.setLogId(rs.getLong("log_id"));
            log.setAircraftId(rs.getLong("aircraft_id"));
            log.setFlightId(rs.getLong("flight_id"));
            log.setMaintenanceDate(rs.getDate("maintenance_date").toLocalDate());
            log.setStatus(rs.getString("status"));
            return log;
        }
    };
    
    private RowMapper<AircraftMaintenanceLog> maintenanceLogWithDetailsRowMapper = new RowMapper<AircraftMaintenanceLog>() {
        @Override
        public AircraftMaintenanceLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            AircraftMaintenanceLog log = new AircraftMaintenanceLog();
            log.setLogId(rs.getLong("log_id"));
            log.setAircraftId(rs.getLong("aircraft_id"));
            log.setFlightId(rs.getLong("flight_id"));
            log.setMaintenanceDate(rs.getDate("maintenance_date").toLocalDate());
            log.setStatus(rs.getString("status"));
            log.setAircraftName(rs.getString("aircraft_name"));
            log.setFlightDetails(rs.getString("flight_details"));
            return log;
        }
    };
    
    // CREATE
    public int create(AircraftMaintenanceLog log) {
        String sql = "INSERT INTO aircraft_maintenance_log (aircraft_id, flight_id, maintenance_date, status) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, log.getAircraftId(), log.getFlightId(), 
                                 log.getMaintenanceDate(), log.getStatus());
    }
    
    // READ ALL WITH DETAILS
    public List<AircraftMaintenanceLog> findAllWithDetails() {
        String sql = "SELECT aml.*, " +
                    "a.name as aircraft_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details " +
                    "FROM aircraft_maintenance_log aml " +
                    "LEFT JOIN aircraft a ON aml.aircraft_id = a.aircraft_id " +
                    "LEFT JOIN flights f ON aml.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id";
        return jdbcTemplate.query(sql, maintenanceLogWithDetailsRowMapper);
    }
    
    // READ BY ID WITH DETAILS
    public AircraftMaintenanceLog findByIdWithDetails(Long logId) {
        String sql = "SELECT aml.*, " +
                    "a.name as aircraft_name, " +
                    "CONCAT(origin.code, ' to ', dest.code, ' - ', f.departure_time) as flight_details " +
                    "FROM aircraft_maintenance_log aml " +
                    "LEFT JOIN aircraft a ON aml.aircraft_id = a.aircraft_id " +
                    "LEFT JOIN flights f ON aml.flight_id = f.flight_id " +
                    "LEFT JOIN airports origin ON f.origin_airport_id = origin.airport_id " +
                    "LEFT JOIN airports dest ON f.destination_airport_id = dest.airport_id " +
                    "WHERE aml.log_id = ?";
        return jdbcTemplate.queryForObject(sql, maintenanceLogWithDetailsRowMapper, logId);
    }
    
    // UPDATE
    public int update(AircraftMaintenanceLog log) {
        String sql = "UPDATE aircraft_maintenance_log SET aircraft_id = ?, flight_id = ?, maintenance_date = ?, status = ? WHERE log_id = ?";
        return jdbcTemplate.update(sql, log.getAircraftId(), log.getFlightId(), 
                                 log.getMaintenanceDate(), log.getStatus(), log.getLogId());
    }
    
    // DELETE
    public int delete(Long logId) {
        String sql = "DELETE FROM aircraft_maintenance_log WHERE log_id = ?";
        return jdbcTemplate.update(sql, logId);
    }
    
    // FIND BY AIRCRAFT
    public List<AircraftMaintenanceLog> findByAircraft(Long aircraftId) {
        String sql = "SELECT * FROM aircraft_maintenance_log WHERE aircraft_id = ?";
        return jdbcTemplate.query(sql, maintenanceLogRowMapper, aircraftId);
    }
    
    // FIND BY STATUS
    public List<AircraftMaintenanceLog> findByStatus(String status) {
        String sql = "SELECT * FROM aircraft_maintenance_log WHERE status = ?";
        return jdbcTemplate.query(sql, maintenanceLogRowMapper, status);
    }
    
    // FIND BY DATE RANGE
    public List<AircraftMaintenanceLog> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM aircraft_maintenance_log WHERE maintenance_date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, maintenanceLogRowMapper, startDate, endDate);
    }
}
