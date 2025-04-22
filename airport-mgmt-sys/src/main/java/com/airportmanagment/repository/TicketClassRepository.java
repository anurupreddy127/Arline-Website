package com.airportmanagment.repository;

import com.airportmanagment.model.TicketClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketClassRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TicketClassRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<TicketClass> ticketClassRowMapper = new RowMapper<TicketClass>() {
        @Override
        public TicketClass mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketClass ticketClass = new TicketClass();
            ticketClass.setClassId(rs.getLong("class_id"));
            ticketClass.setName(rs.getString("name"));
            ticketClass.setBenefits(rs.getString("benefits"));
            return ticketClass;
        }
    };
    
    // CREATE
    public int create(TicketClass ticketClass) {
        String sql = "INSERT INTO ticket_classes (name, benefits) VALUES (?, ?)";
        return jdbcTemplate.update(sql, ticketClass.getName(), ticketClass.getBenefits());
    }
    
    // READ ALL
    public List<TicketClass> findAll() {
        String sql = "SELECT * FROM ticket_classes";
        return jdbcTemplate.query(sql, ticketClassRowMapper);
    }
    
    // READ BY ID
    public TicketClass findById(Long classId) {
        String sql = "SELECT * FROM ticket_classes WHERE class_id = ?";
        return jdbcTemplate.queryForObject(sql, ticketClassRowMapper, classId);
    }
    
    // READ BY NAME
    public TicketClass findByName(String name) {
        String sql = "SELECT * FROM ticket_classes WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, ticketClassRowMapper, name);
    }
    
    // UPDATE
    public int update(TicketClass ticketClass) {
        String sql = "UPDATE ticket_classes SET name = ?, benefits = ? WHERE class_id = ?";
        return jdbcTemplate.update(sql, ticketClass.getName(), ticketClass.getBenefits(), ticketClass.getClassId());
    }
    
    // DELETE
    public int delete(Long classId) {
        String sql = "DELETE FROM ticket_classes WHERE class_id = ?";
        return jdbcTemplate.update(sql, classId);
    }
}