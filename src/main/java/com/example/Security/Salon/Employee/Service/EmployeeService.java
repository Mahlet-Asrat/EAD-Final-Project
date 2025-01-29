package com.example.Security.Salon.Employee.Service;

import com.example.Security.Salon.Employee.Model.Dto.CreateEmployeeDto;
import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Employee.Model.Speciality;
import com.example.Security.Salon.Employee.Service.Repositories.EmployeeRepository;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalonService salonService;

    @Override
    public Employee getEmployee(UUID id) throws ResourceNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public ArrayList<Employee> getAllEmployeesForASalon(UUID salonId) throws ResourceNotFoundException {
        List<Employee> employees = employeeRepository.findAllBySalonId(salonId);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found for the given salon");
        }
        return new ArrayList<>(employees);
    }

    @Override
    public Employee addEEmployee(CreateEmployeeDto createEmployeeDto) throws ResourceNotFoundException {

        Salon salon = salonService.getSalonById(createEmployeeDto.getSalonId())
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found"));
//        boolean employeeExists = employeeRepository.existsByNameAndRoleAndSpecialtiesAndAvailabilityAndExperienceAndImageAndSalonId(
//                createEmployeeDto.getName(),
//                createEmployeeDto.getRole(),
//                createEmployeeDto.getSpecialties().stream().map(Speciality::valueOf).toList(),
//                createEmployeeDto.getAvailability(),
//                createEmployeeDto.getExperience(),
//                createEmployeeDto.getImage(),
//                createEmployeeDto.getSalonId()
//        );
//
//        if (employeeExists) {
//            throw new IllegalArgumentException("An employee with the same details already exists.");
//        }
        List<Speciality> specialties = createEmployeeDto.getSpecialties().stream()
                .map(Speciality::valueOf)
                .toList();

        Employee newEmployee = new Employee(
                createEmployeeDto.getName(),
                createEmployeeDto.getRole(),
                specialties,
                createEmployeeDto.getAvailability(),
                createEmployeeDto.getExperience(),
                createEmployeeDto.getImage(),
                salon
        );


        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(UUID id, CreateEmployeeDto createEmployeeDto) throws ResourceNotFoundException {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));


        existingEmployee.setName(createEmployeeDto.getName());
        existingEmployee.setRole(createEmployeeDto.getRole());
        List<Speciality> updatedSpecialties = createEmployeeDto.getSpecialties().stream()
                .map(Speciality::valueOf)
                .collect(Collectors.toList());
        existingEmployee.setSpecialties(updatedSpecialties);
        existingEmployee.setAvailability(createEmployeeDto.getAvailability());
        existingEmployee.setExperience(createEmployeeDto.getExperience());
        existingEmployee.setImage(createEmployeeDto.getImage());


        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(UUID id) throws ResourceNotFoundException {

        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }


}
