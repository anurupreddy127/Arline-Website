package com.airportmanagment.repository;

import com.airportmanagment.model.Terminal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TerminalRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TerminalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Terminal> terminalRowMapper = new RowMapper<Terminal>() {
        @Override
        public Terminal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Terminal terminal = new Terminal();
            terminal.setTerminalId(rs.getLong("terminal_id"));
            terminal.setName(rs.getString("name"));
            terminal.setLocation(rs.getString("location"));
            return terminal;
        }
    };
    
    // CREATE
    public int create(Terminal terminal) {
        String sql = "INSERT INTO terminals (name, location) VALUES (?, ?)";
        return jdbcTemplate.update(sql, terminal.getName(), terminal.getLocation());
    }
    
    // READ ALL
    public List<Terminal> findAll() {
        String sql = "SELECT * FROM terminals";
        return jdbcTemplate.query(sql, terminalRowMapper);
    }
    
    // READ BY ID
    public Terminal findById(Long terminalId) {
        String sql = "SELECT * FROM terminals WHERE terminal_id = ?";
        return jdbcTemplate.queryForObject(sql, terminalRowMapper, terminalId);
    }
    
    // UPDATE
    public int update(Terminal terminal) {
        String sql = "UPDATE terminals SET name = ?, location = ? WHERE terminal_id = ?";
        return jdbcTemplate.update(sql, terminal.getName(), terminal.getLocation(), terminal.getTerminalId());
    }
    
    // DELETE
    public int delete(Long terminalId) {
        String sql = "DELETE FROM terminals WHERE terminal_id = ?";
        return jdbcTemplate.update(sql, terminalId);
    }
    
    // FIND BY NAME
    public Terminal findByName(String name) {
        String sql = "SELECT * FROM terminals WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, terminalRowMapper, name);
    }
}