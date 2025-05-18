package com.first.springbp.dao;

import java.util.List;

import com.first.springbp.model.Employee;
public interface employeeDAO {
    
    int save(Employee emp);

    int update(Employee emp, int id);

    int delete(int id);

    List<Employee> getAll();
    
    Employee getById(int id);
}
