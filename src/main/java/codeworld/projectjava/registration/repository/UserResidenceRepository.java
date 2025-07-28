package codeworld.projectjava.registration.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import codeworld.projectjava.registration.model.UserResidence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserResidenceRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserResidenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(UserResidence userResidence) {
        String sql = "INSERT INTO user_residence (user_id, residence_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userResidence.getUserId(), userResidence.getResidenceId());
    }

    public List<UserResidence> findByUserId(Long userId) {
        String sql = "SELECT * FROM user_residence WHERE user_id = ?";
        return jdbcTemplate.query(sql, new UserResidenceRowMapper(), userId);
    }

    public List<UserResidence> findByResidenceId(Long residenceId) {
        String sql = "SELECT * FROM user_residence WHERE residence_id = ?";
        return jdbcTemplate.query(sql, new UserResidenceRowMapper(), residenceId);
    }

    public void deleteAssociation(Long userId, Long residenceId) {
        String sql = "DELETE FROM user_residence WHERE user_id = ? AND residence_id = ?";
        jdbcTemplate.update(sql, userId, residenceId);
    }

    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM user_residence WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void deleteByResidenceId(Long residenceId) {
        String sql = "DELETE FROM user_residence WHERE residence_id = ?";
        jdbcTemplate.update(sql, residenceId);
    }

    private static class UserResidenceRowMapper implements RowMapper<UserResidence> {
        @Override
        public UserResidence mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserResidence(
                rs.getLong("user_id"),
                rs.getLong("residence_id")
            );
        }
    }
}