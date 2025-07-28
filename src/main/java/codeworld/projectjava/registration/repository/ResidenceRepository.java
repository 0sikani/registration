package codeworld.projectjava.registration.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import codeworld.projectjava.registration.model.Residence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ResidenceRepository {
    private final JdbcTemplate jdbcTemplate;

    public ResidenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Residence save(Residence residence) {
        if (residence.getId() == null) {
            String sql = "INSERT INTO residence (physical_address, digital_address, city) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, residence.getPhysicalAddress(), residence.getDigitalAddress(), residence.getCity());
            
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            residence.setId(id);
        } else {
            String sql = "UPDATE residence SET physical_address = ?, digital_address = ?, city = ? WHERE id = ?";
            jdbcTemplate.update(sql, residence.getPhysicalAddress(), residence.getDigitalAddress(), residence.getCity(), residence.getId());
        }
        return residence;
    }

    public Optional<Residence> findById(Long id) {
        String sql = "SELECT * FROM residence WHERE id = ?";
        try {
            Residence residence = jdbcTemplate.queryForObject(sql, new ResidenceRowMapper(), id);
            return Optional.ofNullable(residence);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Residence> findAll() {
        String sql = "SELECT * FROM residence";
        return jdbcTemplate.query(sql, new ResidenceRowMapper());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM residence WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ResidenceRowMapper implements RowMapper<Residence> {
        @Override
        public Residence mapRow(ResultSet rs, int rowNum) throws SQLException {
            Residence residence = new Residence();
            residence.setId(rs.getLong("id"));
            residence.setPhysicalAddress(rs.getString("physical_address"));
            residence.setDigitalAddress(rs.getString("digital_address"));
            residence.setCity(rs.getString("city"));
            return residence;
        }
    }
}