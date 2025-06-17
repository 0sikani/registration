package codeworld.projectjava.registration.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.registration.model.Residence;
import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.repository.UserRepository.UserRowMapper;

@Repository
public class ResidenceRepository {

    private final JdbcTemplate jdTemp;

    public ResidenceRepository(JdbcTemplate jdTemp){
        this.jdTemp = jdTemp;
    }

    //store or update residence
    public Residence save(Residence residence){
        if(residence.getId() == null){
           String sql = "INSERT INTO residence(physical_address, digital_address, city) VALUES(?, ?, ?)";
           jdTemp.update(sql, residence.getPhysicalAddress(), residence.getDigitalAddress(), residence.getCity());

           Long id = jdTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
           residence.setId(id);
        } else{
            String sql = "UPDATE residence SET physical_addree = ?, digital_address = ?, city = ? WHERE id = ?";
            jdTemp.update(sql, residence.getPhysicalAddress(), residence.getDigitalAddress(), residence.getCity(), residence.getId());
        }
        return residence;
    }
    //fetch all residence
    public List<Residence> getResidence(){
        String sql = "SELECT * FROM residence";
        List<Residence> residence = jdTemp.query(sql, new ResidenceRowMapper());
        residence.forEach(this::loadResidenceUser);
        return residence;
    }
    //fetch a single residence
    public Optional<Residence> getResidenceById(Long id){
        String sql = "SELECT * FROM residence WHERE id = ?";
        return jdTemp.query(sql, new ResidenceRowMapper(), id).stream().findFirst();
    }

    //find residence by userId
    //load residence users
    public void loadResidenceUser(Residence residence){
        String sql = "SELECT a.id, a.user_name, a.phone, a.email FROM user a " +
                     "JOIN user_residence b ON a.id = b.user_id " +
                     "JOIN residence c ON b.residence_id = c.id WHERE c.id = ? ";

        List<User> user = jdTemp.query(sql, new UserRowMapper(), residence.getId());
        residence.setResidenceUser(user);
    }

    //delete residence
    public void deleteResidence(Long id){
        String Sql = "DELETE FROM residence WHERE id = ?";
        jdTemp.update(Sql, id);
    }


    //RowMapper classes definition
    public static class ResidenceRowMapper implements RowMapper<Residence>{

        @Override
        public Residence mapRow(ResultSet res, int row) throws SQLException {
            return new Residence(
                res.getLong("id"),
                res.getString("physical_address"),
                res.getString("digital_address"),
                res.getString("city")
            );   
        }
        
    }
    
}
