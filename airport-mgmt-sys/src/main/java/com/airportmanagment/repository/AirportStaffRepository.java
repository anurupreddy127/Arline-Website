package com.airportmanagment.repository;

import com.airportmanagment.model.AirportStaff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AirportStaffRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AirportStaffRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<AirportStaff> staffRowMapper = new RowMapper<AirportStaff>() {
        @Override
        public AirportStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
            AirportStaff staff = new AirportStaff();
            staff.setStaffId(rs.getLong("staff_id"));
            staff.setName(rs.getString("name"));
            staff.setRole(rs.getString("role"));
            staff.setTerminalId(rs.getLong("terminal_id"));
            return staff;
        }
    };
    
    private RowMapper<AirportStaff> staffWithDetailsRowMapper = new RowMapper<AirportStaff>() {
        @Override
        public AirportStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
            AirportStaff staff = new AirportStaff();
            staff.setStaffId(rs.getLong("staff_id"));
            staff.setName(rs.getString("name"));
            staff.setRole(rs.getString("role"));
            staff.setTerminalId(rs.getLong("terminal_id"));
            staff.setTerminalName(rs.getString("terminal_name"));
            return staff;
        }
    };
    
    // CREATE
    public int create(AirportStaff staff) {
        String sql = "INSERT INTO airport_staff (name, role, terminal_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, staff.getName(), staff.getRole(), staff.getTerminalId());
    }
    
    // READ ALL WITH DETAILS
    public List<AirportStaff> findAllWithDetails() {
        String sql = "SELECT s.*, t.name as terminal_name " +
                    "FROM airport_staff s " +
                    "LEFT JOIN terminals t ON s.terminal_id = t.terminal_id";
        return jdbcTemplate.query(sql, staffWithDetailsRowMapper);
    }
    
    // READ BY ID WITH DETAILS
    public AirportStaff findByIdWithDetails(Long staffId) {
        String sql = "SELECT s.*, t.name as terminal_name " +
                    "FROM airport_staff s " +
                    "LEFT JOIN terminals t ON s.terminal_id = t.terminal_id " +
                    "WHERE s.staff_id = ?";
        return jdbcTemplate.queryForObject(sql, staffWithDetailsRowMapper, staffId);
    }
    
    // UPDATE
    public int update(AirportStaff staff) {
        String sql = "UPDATE airport_staff SET name = ?, role = ?, terminal_id = ? WHERE staff_id = ?";
        return jdbcTemplate.update(sql, staff.getName(), staff.getRole(), 
                                 staff.getTerminalId(), staff.getStaffId());
    }
    
    // DELETE
    public int delete(Long staffId) {
        String sql = "DELETE FROM airport_staff WHERE staff_id = ?";
        return jdbcTemplate.update(sql, staffId);
    }
    
    // FIND BY TERMINAL
    public List<AirportStaff> findByTerminal(Long terminalId) {
        String sql = "SELECT * FROM airport_staff WHERE terminal_id = ?";
        return jdbcTemplate.query(sql, staffRowMapper, terminalId);
    }
    
    // FIND BY ROLE
    public List<AirportStaff> findByRole(String role) {
        String sql = "SELECT * FROM airport_staff WHERE role = ?";
        return jdbcTemplate.query(sql, staffRowMapper, role);
    }
}