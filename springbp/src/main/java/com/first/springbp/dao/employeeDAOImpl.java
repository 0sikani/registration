package com.first.springbp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.first.springbp.model.Employee;

@Repository
public class employeeDAOImpl implements employeeDAO{

    @Autowired
    JdbcTemplate jtemp;
    
    @Override
    public int save(Employee emp){
        return jtemp.update("insert into register (name, telephone, dig_address) values(?, ?, ?)", 
        new Object[] {emp.getName(), emp.getTelephone(), emp.getDig_address()});
    }  
    
    
    @Override
    public int update(Employee emp, int id){
        return jtemp.update("update register set name=?, telephone=?, dig_address=? where id=?", 
        new Object[] {emp.getName(), emp.getTelephone(), emp.getDig_address()}, id);
    }


    @Override
    public int delete(int id){
        return jtemp.update("delete from register where=?", id);
    }

    @Override
    public List<Employee> getAll(){
        return jtemp.query("select * from register", 
        new BeanPropertyRowMapper<>(Employee.class));
    }
    

    @Override
    public Employee getById(int id){
        return jtemp.queryForObject("select * from register where id=?", 
        new BeanPropertyRowMapper<>(Employee.class), id);
    }
}
