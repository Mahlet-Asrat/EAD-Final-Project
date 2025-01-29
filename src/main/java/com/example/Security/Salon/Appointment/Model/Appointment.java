package com.example.Security.Salon.Appointment.Model;

import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private Date dateTime;

    @Column
    private int rating;

    @Column(nullable = true)
    private String comment;


    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "appointment_services",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name="salon_id")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Appointment(Date dateTime, Status status, List<Service> services, Employee employee, Salon salon,User user) {
        this.dateTime = dateTime;
        this.status = status;
        this.services = services;
        this.employee = employee;
        this.salon = salon;
        this.user = user;
    }







}
