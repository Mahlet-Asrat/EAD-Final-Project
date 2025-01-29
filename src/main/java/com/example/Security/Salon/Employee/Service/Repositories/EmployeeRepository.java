package com.example.Security.Salon.Employee.Service.Repositories;

import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Employee.Model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findAllBySalonId(UUID salonId);
//    boolean existsByNameAndRoleAndSpecialtiesAndAvailabilityAndExperienceAndImageAndSalonId(
//            String name,
//            String role,
//            List<Speciality> specialties,
//            String availability,
//            int experience,
//            String image,
//            UUID salonId
//    );
}
