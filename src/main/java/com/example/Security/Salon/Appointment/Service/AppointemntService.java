package com.example.Security.Salon.Appointment.Service;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Dto.CreateAppointmentDTO;
import com.example.Security.Salon.Appointment.Model.Status;
import com.example.Security.Salon.Appointment.Service.Repository.AppointmentRespository;
import com.example.Security.Salon.Employee.Model.Employee;
import com.example.Security.Salon.Employee.Service.EmployeeService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Service.Services.ServiceService;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class AppointemntService implements IAppointmentService {

    @Autowired
    private AppointmentRespository appointmentRepository;


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalonService salonService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceService serviceService;


    @Transactional
    @Override
    public Appointment addAppointment(CreateAppointmentDTO createAppointmentDTO) throws Exception {


        Optional<Appointment> existingAppointment = appointmentRepository.findByEmployee_IdAndSalon_IdAndDateTime(
                createAppointmentDTO.getEmployeeId(),
                createAppointmentDTO.getSalonId(),
                createAppointmentDTO.getDateTime()
        );

        if (existingAppointment.isPresent()) {
            throw new Exception("An appointment already exists at this time for this employee and salon.");
        }

        // Fetch Employee, Salon, and User from their respective services
        Employee employee = employeeService.getEmployee(createAppointmentDTO.getEmployeeId());
        Salon salon = salonService.getSalonById(createAppointmentDTO.getSalonId())
                .orElseThrow(() -> new Exception("Salon not found"));

        User user = userService.findUserById(createAppointmentDTO.getUserId());


        List<com.example.Security.Salon.Service.Model.Service> services = serviceService.getServiceByIds(createAppointmentDTO.getServiceIds());



        Appointment appointment = new Appointment(
                createAppointmentDTO.getDateTime(),
                Status.valueOf(createAppointmentDTO.getStatus()),  // Assuming Status is an enum
                services,
                employee,
                salon,
                user
        );


        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointment(UUID id) throws ResourceNotFoundException {
        return appointmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appointment Not Found"));
    }

    @Override
    public Appointment updateAppointment(UUID id, CreateAppointmentDTO createAppointmentDto) {
        return null;
    }



    @Override
    public void deleteAppointment(UUID id) throws ResourceNotFoundException {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appointment Not Found"));
        appointmentRepository.delete(appointment);

    }

    @Override
    public List<Appointment> getAllAppointmentsForEmployee(UUID employeeId) throws Exception {
        List<Appointment> appointment =  this.appointmentRepository.findByEmployee_Id(employeeId);
        if (appointment.isEmpty()){
            throw  new ResourceNotFoundException("Wrong Employee Id");
        }
        return appointment;

    }

    @Override
    public List<Appointment> getAllAppointmentsForSalon(UUID salonId) throws Exception {
        List<Appointment> appointments = appointmentRepository.findBySalon_Id(salonId) ;
        if(appointments.isEmpty()){
            throw  new ResourceNotFoundException("Wrong Salon Id");
        }
        return appointments;
    }

    @Override
    public String addRatingToAppointment(UUID appointmentID, int rating) throws Exception {

        Appointment appointment = appointmentRepository.findById(appointmentID).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
        if ( appointment.getStatus().toString().equals("COMPLETED") ){
            appointment.setRating(rating);

        }

        List<Appointment> appoint = appointmentRepository.findByEmployee_IdAndStatus(appointment.getEmployee().getId(), Status.COMPLETED);
        int sum = 0;
        appoint.stream().mapToInt(
                appontment->
                        (int)appointment.getRating())
                         .sum();
        float employeesRating = (!appoint.isEmpty())? (float) sum /appoint.size(): 0;
        appointment.getEmployee().setRating(employeesRating);

        return "";



    }


}
