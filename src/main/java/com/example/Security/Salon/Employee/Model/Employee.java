package com.example.Security.Salon.Employee.Model;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Salon.Model.Salon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

    private UUID id;

    private String name;

    private String role;

    @ElementCollection(targetClass = Speciality.class)
    @Enumerated(EnumType.STRING)
    private List<Speciality> specialties;

    @Lob
    private String availability;

    private int experience;

    private String image;

    private float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salon_id")
    private Salon salon;


    @OneToMany(mappedBy = "employee",  cascade = CascadeType.ALL)
    private List<Appointment> appointment;

    public Employee(String name, String role, List<Speciality> specialty, String availability, int experience, String image, Salon salon) {
        this.name = name;
        this.role = role;
        this.specialties = specialty;
        this.availability = availability;
        this.experience = experience;
        this.image = image;
        this.salon = salon;
    }


}
