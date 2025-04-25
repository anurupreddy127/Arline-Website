package com.airportmanagment.repository;


import com.airportmanagment.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }
    };

    public int create(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole());
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }

    public int update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<User> findByRole(String role) {
        String sql = "SELECT * FROM users WHERE role = ?";
        return jdbcTemplate.query(sql, userRowMapper, role);
    }
}
