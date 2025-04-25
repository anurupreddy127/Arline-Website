package com.airportmanagment.repository;

import com.airportmanagment.model.Lounge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LoungeRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public LoungeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Lounge> loungeRowMapper = new RowMapper<Lounge>() {
        @Override
        public Lounge mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lounge lounge = new Lounge();
            lounge.setLoungeId(rs.getLong("lounge_id"));
            lounge.setLocation(rs.getString("location"));
            lounge.setLoyaltyId(rs.getString("loyalty_id"));
            lounge.setTerminalId(rs.getLong("terminal_id"));
            return lounge;
        }
    };
    
    private RowMapper<Lounge> loungeWithDetailsRowMapper = new RowMapper<Lounge>() {
        @Override
        public Lounge mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lounge lounge = new Lounge();
            lounge.setLoungeId(rs.getLong("lounge_id"));
            lounge.setLocation(rs.getString("location"));
            lounge.setLoyaltyId(rs.getString("loyalty_id"));
            lounge.setTerminalId(rs.getLong("terminal_id"));
            lounge.setTerminalName(rs.getString("terminal_name"));
            return lounge;
        }
    };

    public int create(Lounge lounge) {
        String sql = "INSERT INTO lounges (location, loyalty_id, terminal_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, lounge.getLocation(), lounge.getLoyaltyId(), lounge.getTerminalId());
    }

    public List<Lounge> findAllWithDetails() {
        String sql = "SELECT l.*, t.name as terminal_name " +
                    "FROM lounges l " +
                    "LEFT JOIN terminals t ON l.terminal_id = t.terminal_id";
        return jdbcTemplate.query(sql, loungeWithDetailsRowMapper);
    }

    public Lounge findByIdWithDetails(Long loungeId) {
        String sql = "SELECT l.*, t.name as terminal_name " +
                    "FROM lounges l " +
                    "LEFT JOIN terminals t ON l.terminal_id = t.terminal_id " +
                    "WHERE l.lounge_id = ?";
        return jdbcTemplate.queryForObject(sql, loungeWithDetailsRowMapper, loungeId);
    }

    public int update(Lounge lounge) {
        String sql = "UPDATE lounges SET location = ?, loyalty_id = ?, terminal_id = ? WHERE lounge_id = ?";
        return jdbcTemplate.update(sql, lounge.getLocation(), lounge.getLoyaltyId(), 
                                 lounge.getTerminalId(), lounge.getLoungeId());
    }

    public int delete(Long loungeId) {
        String sql = "DELETE FROM lounges WHERE lounge_id = ?";
        return jdbcTemplate.update(sql, loungeId);
    }

    public List<Lounge> findByTerminal(Long terminalId) {
        String sql = "SELECT * FROM lounges WHERE terminal_id = ?";
        return jdbcTemplate.query(sql, loungeRowMapper, terminalId);
    }

    public List<Lounge> findByLoyaltyId(String loyaltyId) {
        String sql = "SELECT * FROM lounges WHERE loyalty_id = ?";
        return jdbcTemplate.query(sql, loungeRowMapper, loyaltyId);
    }
}