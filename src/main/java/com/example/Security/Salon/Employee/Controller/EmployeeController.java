package com.example.Security.Salon.Employee.Controller;

import com.example.Security.Salon.Employee.Model.Dto.CreateEmployeeDto;
import com.example.Security.Salon.Employee.Model.Dto.EmployeeResponseDto;
import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Employee.Service.EmployeeService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.baseUrl}/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Validated @RequestBody CreateEmployeeDto dto) {
        try {
            Employee employee = employeeService.addEEmployee(dto);
            EmployeeResponseDto responseDto = new EmployeeResponseDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getSpecialties(),
                    employee.getAvailability(),
                    employee.getExperience(),
                    employee.getImage(),
                    employee.getSalon().getName(),
                    employee.getSalon().getId(),
                     new Date(),
                    null
                            );

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{salonId}/salon")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesBySalon(@PathVariable UUID salonId) {
        try {
            List<Employee> employees = employeeService.getAllEmployeesForASalon(salonId);
            List<EmployeeResponseDto> responseDto = employees.stream()
                    .map(employee->
                     new EmployeeResponseDto(
                            employee.getId(),
                            employee.getName(),
                            employee.getRole(),
                            employee.getSpecialties(),
                            employee.getAvailability(),
                            employee.getExperience(),
                            employee.getImage(),
                            employee.getSalon().getName(),
                            employee.getSalon().getId(),
                            null,
                            null


                    ))
                    .collect(Collectors.toList());




            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('EMPLOYEE')")
    @GetMapping("/{id}/employee")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable UUID id) {
        try {
            Employee employee = employeeService.getEmployee(id);
            EmployeeResponseDto responseDto = new EmployeeResponseDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getSpecialties(),
                    employee.getAvailability(),
                    employee.getExperience(),
                    employee.getImage(),
                    employee.getSalon().getName(),
                    employee.getSalon().getId(),
                    null,
                    null
            );
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable UUID id,
                                                              @Validated @RequestBody CreateEmployeeDto dto) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, dto);
            EmployeeResponseDto responseDto = new EmployeeResponseDto(updatedEmployee.getId(),
                    updatedEmployee.getName(),
                    updatedEmployee.getRole(),
                    updatedEmployee.getSpecialties(),
                    updatedEmployee.getAvailability(),
                    updatedEmployee.getExperience(),
                    updatedEmployee.getImage(),
                    updatedEmployee.getSalon().getName(),
                    updatedEmployee.getSalon().getId(),
                    null,
                    new Date());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}/employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
