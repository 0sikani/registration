package codeworld.projectjava.registration.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import codeworld.projectjava.registration.model.Residence;
import codeworld.projectjava.registration.model.User;
import codeworld.projectjava.registration.model.UserResidence;
import codeworld.projectjava.registration.repository.ResidenceRepository.ResidenceRowMapper;

@Repository
public class UserRepository {
    private final JdbcTemplate jdTemp;
    private final UserResidenceRepository urRepo;

    public UserRepository(JdbcTemplate jdTemp, UserResidenceRepository urRepo){
        this.jdTemp = jdTemp;
        this.urRepo = urRepo;
    }

    //store a user or update a user
    public Map<String, Object> save(User user, Long residence_id){
        Map<String, Object> response = new HashMap<>();
        String message;

        if(user.getId() == null){

            if(checkResidence(residence_id)){
                UserResidence ur = new UserResidence();
                
                String sql = "INSERT INTO user(user_name, phone, email) VALUES(?,?,?)";
                jdTemp.update(sql, user.getUserName(), user.getPhone(), user.getEmail());

                Long id = jdTemp.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
                user.setId(id);

                storeUR(ur, urRepo, id, residence_id);

                message = "user details stored successfully";
                response.put("message", message);
                response.put("user", user);

            } else{
                message = "residence doesn't exist please check and try again......";
                response.put("message", message);
            }
            
        } else{
            String sql = "UPDATE user SET user_name = ?, phone = ?, email = ? WHERE id = ?";
            jdTemp.update(sql, user.getUserName(), user.getPhone(), user.getEmail(), user.getId());
        }
        return response;
    }
    
 
    public Page<User> getUsers(Pageable pageable){
        String countRec = "SELECT COUNT(*) FROM user";
        int sumRec = jdTemp.queryForObject(countRec, Integer.class);

        String sql = "SELECT * FROM user LIMIT ? OFFSET ?";
        List<User> users = jdTemp.query(
            sql, 
            new UserRowMapper(),
            pageable.getPageSize(),
            pageable.getOffset()
        );

        users.forEach(this::loadUserResidence);
        return new PageImpl<>(users, pageable, sumRec);
    }

    //fetch a single user
    public Optional<User> getUserById(Long id){
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdTemp.query(sql, new UserRowMapper(), id).stream().peek(this::loadUserResidence).findFirst();
    } 

    //login logic
    // public Map<String, Object> login(String email, String password){
    //     Map<String, Object> response = new HashMap<>();

    //     String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
    //     User user = jdTemp.queryForObject(sql, new UserRowMapper(), email, password);

    //     if(user != null){
    //         boolean message = true;
    //         response.put("message", message);
    //         response.put("user", user);
    //     }
    //     else{
    //         boolean message = false;
    //         response.put("message", message);
    //     }
    //     return response;
    // }

    //find user by email
    public Optional<User> findUserByEmail(String email) {

        String sql = "SELECT * FROM user WHERE email = ?";
    
        try {
                User user = jdTemp.queryForObject(sql, new UserRowMapper(), email);
                return Optional.ofNullable(user);
            } 
        catch (EmptyResultDataAccessException e) {
                return Optional.empty();
        }
    }
    
    //store user residence
    private void storeUR(UserResidence userResidence, UserResidenceRepository userResidenceRepo, Long userId, Long residenceId){
        userResidence.setUserId(userId);
        userResidence.setResidenceId(residenceId);
        userResidenceRepo.storeUserResidence(userResidence);
    }

    //find residence by userId

    //load user residence
    private void loadUserResidence(User user){
        String sql = "SELECT a.id, a.physical_address, a.digital_address, a.city FROM residence a " +
                     "JOIN user_residence b ON a.id = b.residence_id " +
                     "JOIN user c ON b.user_id = c.id WHERE c.id = ? ";

        List<Residence> residence = jdTemp.query(sql, new ResidenceRowMapper(), user.getId());
        user.setUserResidence(residence);
    }
    //delete user
    public void deleteUser(Long id){
        String sql = "DELETE FROM user WHERE id = ?";
        jdTemp.update(sql, id);
    }

    //check existence of residence 
    public Boolean checkResidence(Long id){
        String sql = "SELECT COUNT(*) FROM residence WHERE id = " + id;
        Integer result = jdTemp.queryForObject(sql, Integer.class);
        if(result != 0);
        return true;
    }

    //RowMapper classes definition
    public static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet res, int row) throws SQLException{
            return new User(
                res.getLong("id"),
                res.getString("user_name"),
                res.getString("phone"),
                res.getString("email"),
                res.getString("password")
            );
        }
    }
}
