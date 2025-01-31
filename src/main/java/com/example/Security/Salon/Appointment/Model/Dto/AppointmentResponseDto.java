package com.example.Security.Salon.Appointment.Model.Dto;

import com.example.Security.Salon.Appointment.Model.AppointmentService;
import com.example.Security.Salon.Appointment.Model.Status;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AppointmentResponseDto {

    private UUID appointmentId;
    private Date dateTime;
    private Status status;
    private User user;
    private List<AppointmentService> appointmentsServices;


    public AppointmentResponseDto(UUID appointmentId, Date dateTime, Status status, User user, List<AppointmentService> appointmentsServices) {
        this.appointmentId = appointmentId;
        this.dateTime = dateTime;
        this.status = status;
        this.user = user;
        this.appointmentsServices = appointmentsServices;
    }


}
