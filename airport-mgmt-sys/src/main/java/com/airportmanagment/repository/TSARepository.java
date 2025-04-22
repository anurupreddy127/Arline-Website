package com.airportmanagment.repository;

import com.airportmanagment.model.TSA;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TSARepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TSARepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<TSA> tsaRowMapper = new RowMapper<TSA>() {
        @Override
        public TSA mapRow(ResultSet rs, int rowNum) throws SQLException {
            TSA tsa = new TSA();
            tsa.setPersonalId(rs.getLong("personal_id"));
            tsa.setName(rs.getString("name"));
            tsa.setClearanceLevel(rs.getString("clearance_level"));
            tsa.setCheckpointNumber(rs.getInt("checkpoint_number"));
            tsa.setTerminalId(rs.getLong("terminal_id"));
            return tsa;
        }
    };
    
    private RowMapper<TSA> tsaWithDetailsRowMapper = new RowMapper<TSA>() {
        @Override
        public TSA mapRow(ResultSet rs, int rowNum) throws SQLException {
            TSA tsa = new TSA();
            tsa.setPersonalId(rs.getLong("personal_id"));
            tsa.setName(rs.getString("name"));
            tsa.setClearanceLevel(rs.getString("clearance_level"));
            tsa.setCheckpointNumber(rs.getInt("checkpoint_number"));
            tsa.setTerminalId(rs.getLong("terminal_id"));
            tsa.setTerminalName(rs.getString("terminal_name"));
            return tsa;
        }
    };
    
    // CREATE
    public int create(TSA tsa) {
        String sql = "INSERT INTO tsa (name, clearance_level, checkpoint_number, terminal_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tsa.getName(), tsa.getClearanceLevel(), 
                                 tsa.getCheckpointNumber(), tsa.getTerminalId());
    }
    
    // READ ALL WITH DETAILS
    public List<TSA> findAllWithDetails() {
        String sql = "SELECT tsa.*, t.name as terminal_name " +
                    "FROM tsa " +
                    "LEFT JOIN terminals t ON tsa.terminal_id = t.terminal_id";
        return jdbcTemplate.query(sql, tsaWithDetailsRowMapper);
    }
    
    // READ BY ID WITH DETAILS
    public TSA findByIdWithDetails(Long personalId) {
        String sql = "SELECT tsa.*, t.name as terminal_name " +
                    "FROM tsa " +
                    "LEFT JOIN terminals t ON tsa.terminal_id = t.terminal_id " +
                    "WHERE tsa.personal_id = ?";
        return jdbcTemplate.queryForObject(sql, tsaWithDetailsRowMapper, personalId);
    }
    
    // UPDATE
    public int update(TSA tsa) {
        String sql = "UPDATE tsa SET name = ?, clearance_level = ?, checkpoint_number = ?, terminal_id = ? " +
                    "WHERE personal_id = ?";
        return jdbcTemplate.update(sql, tsa.getName(), tsa.getClearanceLevel(), 
                                 tsa.getCheckpointNumber(), tsa.getTerminalId(), tsa.getPersonalId());
    }
    
    // DELETE
    public int delete(Long personalId) {
        String sql = "DELETE FROM tsa WHERE personal_id = ?";
        return jdbcTemplate.update(sql, personalId);
    }
    
    // FIND BY TERMINAL
    public List<TSA> findByTerminal(Long terminalId) {
        String sql = "SELECT * FROM tsa WHERE terminal_id = ?";
        return jdbcTemplate.query(sql, tsaRowMapper, terminalId);
    }
    
    // FIND BY CLEARANCE LEVEL
    public List<TSA> findByClearanceLevel(String clearanceLevel) {
        String sql = "SELECT * FROM tsa WHERE clearance_level = ?";
        return jdbcTemplate.query(sql, tsaRowMapper, clearanceLevel);
    }
    
    // FIND BY CHECKPOINT
    public List<TSA> findByCheckpoint(Integer checkpointNumber) {
        String sql = "SELECT * FROM tsa WHERE checkpoint_number = ?";
        return jdbcTemplate.query(sql, tsaRowMapper, checkpointNumber);
    }
}
