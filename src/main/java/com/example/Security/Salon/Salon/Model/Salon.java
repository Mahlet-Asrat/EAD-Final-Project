package com.example.Security.Salon.Salon.Model;


import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Service.Model.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Salon {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

    private UUID id;

    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String openingHours;
    private String image;


    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Service> services;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public Salon(String name, String description, String address, String phoneNumber, String openingHours, String image) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.image = image;


    }

}

