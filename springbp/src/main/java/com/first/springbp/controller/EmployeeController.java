package com.first.springbp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.first.springbp.dao.employeeDAO;
import com.first.springbp.model.Employee;

@RestController
public class EmployeeController {

    @Autowired
    private employeeDAO empDAO;

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return empDAO.getAll();
    }


    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id){
        return empDAO.getById(id);
    }


    @PostMapping("/employee")
    public String signup(@RequestBody Employee emp){
            return empDAO.save(emp) + " insert made";
    }


    @PutMapping("/employee/{id}")
    public String updateDetails(@RequestBody Employee emp, @PathVariable int id){
        return empDAO.update(emp, id) + " row(s) updated";
    }


    @DeleteMapping("/employee/{id}")
    public String deleteDetails(@PathVariable int id){
        return empDAO.delete(id) + " record deleted";
    }
}
