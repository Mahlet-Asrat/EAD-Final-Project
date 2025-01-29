package com.example.Security.Salon.Appointment.Model.Dto;

import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentDTO {

    @NotNull(message = "Date and time cannot be null")
    @Future(message = "The appointment date must be in the future")
    private Date dateTime;


    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Services cannot be null")
    private List<UUID> serviceIds;

    @NotNull(message = "Employee ID cannot be null")
    private UUID employeeId;

    @NotNull(message = "Salon ID cannot be null")
    private UUID salonId;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;


}
