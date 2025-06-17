package codeworld.projectjava.registration.repository;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.registration.model.UserResidence;

@Repository
public class UserResidenceRepository {

    private final JdbcTemplate jTemp;

    public UserResidenceRepository(JdbcTemplate jTemp){
        this.jTemp = jTemp;
    }
    
    //store user residence
    public UserResidence storeUserResidence(UserResidence userResidence){
        String sql = "INSERT INTO user_residence(user_id, residence_id) VALUES(?, ?)";
        jTemp.update(sql, userResidence.getUserId(), userResidence.getResidenceId());
        return  userResidence;
    }

    //fetch user all residence
    public List<UserResidence> getUserResidence(){
        String sql = "SELECT * FROM user_residence";
        return jTemp.query(sql, new UserResidenceMapper());
    }

    //delete user residence
    public void deleteUserResidence(Long id){
        String sql = "DELETE FROM user_residence WHERE user_id = ?";
        jTemp.update(sql, id);
    }

    private static class UserResidenceMapper implements RowMapper<UserResidence>{
        @Override
        public UserResidence mapRow(ResultSet res, int row) throws SQLException{
            return new UserResidence(
                res.getLong("user_id"),
                res.getLong("residence_id")
            );
        }
    }

}
