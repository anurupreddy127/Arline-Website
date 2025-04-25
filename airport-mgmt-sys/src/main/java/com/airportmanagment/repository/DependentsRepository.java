package com.airportmanagment.repository;

import com.airportmanagment.model.Dependents;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DependentsRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public DependentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<Dependents> dependentsRowMapper = new RowMapper<Dependents>() {
        @Override
        public Dependents mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dependents dependent = new Dependents();
            dependent.setDependentId(rs.getLong("dependent_id"));
            dependent.setName(rs.getString("name"));
            dependent.setPhone(rs.getString("phone"));
            dependent.setLoyaltyId(rs.getString("loyalty_id"));
            dependent.setPassengerId(rs.getLong("passenger_id"));
            dependent.setTicketId(rs.getLong("ticket_id"));
            return dependent;
        }
    };
    
    private RowMapper<Dependents> dependentsWithDetailsRowMapper = new RowMapper<Dependents>() {
        @Override
        public Dependents mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dependents dependent = new Dependents();
            dependent.setDependentId(rs.getLong("dependent_id"));
            dependent.setName(rs.getString("name"));
            dependent.setPhone(rs.getString("phone"));
            dependent.setLoyaltyId(rs.getString("loyalty_id"));
            dependent.setPassengerId(rs.getLong("passenger_id"));
            dependent.setTicketId(rs.getLong("ticket_id"));
            dependent.setPassengerName(rs.getString("passenger_name"));
            dependent.setTicketDetails(rs.getString("ticket_details"));
            return dependent;
        }
    };
    
    public int create(Dependents dependent) {
        String sql = "INSERT INTO dependents (name, phone, loyalty_id, passenger_id, ticket_id) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, dependent.getName(), dependent.getPhone(), dependent.getLoyaltyId(), dependent.getPassengerId(), dependent.getTicketId());
    }

    public List<Dependents> findAllWithDetails() {
        String sql = "SELECT d.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(t.seat, ' - $', t.price) as ticket_details " +
                    "FROM dependents d " +
                    "LEFT JOIN passengers p ON d.passenger_id = p.passenger_id " +
                    "LEFT JOIN tickets t ON d.ticket_id = t.ticket_id";
        return jdbcTemplate.query(sql, dependentsWithDetailsRowMapper);
    }

    public Dependents findByIdWithDetails(Long dependentId) {
        String sql = "SELECT d.*, " +
                    "p.name as passenger_name, " +
                    "CONCAT(t.seat, ' - $', t.price) as ticket_details " +
                    "FROM dependents d " +
                    "LEFT JOIN passengers p ON d.passenger_id = p.passenger_id " +
                    "LEFT JOIN tickets t ON d.ticket_id = t.ticket_id " +
                    "WHERE d.dependent_id = ?";
        return jdbcTemplate.queryForObject(sql, dependentsWithDetailsRowMapper, dependentId);
    }

    public int update(Dependents dependent) {
        String sql = "UPDATE dependents SET name = ?, phone = ?, loyalty_id = ?, passenger_id = ?, ticket_id = ? WHERE dependent_id = ?";
        return jdbcTemplate.update(sql, dependent.getName(), dependent.getPhone(), dependent.getLoyaltyId(), dependent.getPassengerId(), dependent.getTicketId(), dependent.getDependentId());
    }

    public int delete(Long dependentId) {
        String sql = "DELETE FROM dependents WHERE dependent_id = ?";
        return jdbcTemplate.update(sql, dependentId);
    }

    public List<Dependents> findByPassenger(Long passengerId) {
        String sql = "SELECT * FROM dependents WHERE passenger_id = ?";
        return jdbcTemplate.query(sql, dependentsRowMapper, passengerId);
    }

    public Dependents findByTicket(Long ticketId) {
        String sql = "SELECT * FROM dependents WHERE ticket_id = ?";
        return jdbcTemplate.queryForObject(sql, dependentsRowMapper, ticketId);
    }

    public List<Dependents> findByLoyaltyId(String loyaltyId) {
        String sql = "SELECT * FROM dependents WHERE loyalty_id = ?";
        return jdbcTemplate.query(sql, dependentsRowMapper, loyaltyId);
    }
}