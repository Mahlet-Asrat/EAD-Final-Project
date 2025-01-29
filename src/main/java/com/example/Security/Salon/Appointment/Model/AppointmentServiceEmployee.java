package com.example.Security.Salon.Appointment.Model;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Service.Model.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentServiceEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private int durationInMinutes;

    public AppointmentServiceEmployee(Appointment appointment, Service service, Employee employee, int durationInMinutes) {
        this.appointment = appointment;
        this.service = service;
        this.employee = employee;
        this.durationInMinutes = durationInMinutes;
    }
}
