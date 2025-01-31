package com.example.Security.Salon.Appointment.Model.Dto;

import com.example.Security.Salon.Appointment.Model.Status;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;

import java.util.List;
import java.util.UUID;

public class AppointmentResponseDto {

    private UUID appointmentId;
    private String dateTime;
    private Status status;
    private User user;
    private List<Service> services;
    private float rating;

    public AppointmentResponseDto(UUID appointmentId, String dateTime, Status status, User user, List<Service> services, float rating) {
        this.appointmentId = appointmentId;
        this.dateTime = dateTime;
        this.status = status;
        this.user = user;
        this.services = services;
        this.rating = rating;
    }


}
