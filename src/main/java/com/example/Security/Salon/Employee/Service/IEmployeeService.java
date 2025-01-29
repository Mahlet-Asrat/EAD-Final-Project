package com.example.Security.Salon.Employee.Service;

import com.example.Security.Salon.Employee.Model.Dto.CreateEmployeeDto;
import com.example.Security.Salon.Employee.Model.Employee;

import java.util.ArrayList;
import java.util.UUID;

public interface IEmployeeService  {
    Employee getEmployee(UUID id) throws Exception;
    ArrayList<Employee> getAllEmployeesForASalon(UUID salonId) throws Exception;
    Employee addEEmployee(CreateEmployeeDto createEmployeeDto) throws Exception;
    Employee updateEmployee(UUID id, CreateEmployeeDto createEmployeeDto) throws Exception;
    void deleteEmployee(UUID id) throws Exception;
}

