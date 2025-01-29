package com.example.Security.Salon.Employee.Model.Dto;

import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Employee.Model.Speciality;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
   private UUID id;

   private String name;

   private String role;

   private List<Speciality> specialty;


   private String availability;

   private int experience;

   private String image;
   private String salonName;
   private UUID salonId;

   private Date createdAt;
   private Date updatedAt;

}


