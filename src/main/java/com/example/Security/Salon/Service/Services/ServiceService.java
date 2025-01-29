package com.example.Security.Salon.Service.Services;

//import com.example.Security.Salon.Appointment.Model.Appointment;
//import com.example.Security.Salon.Appointment.Service.AppointmentService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Service.Model.Dto.CreateServiceDto;
import com.example.Security.Salon.Service.Model.Dto.UpdateServiceDto;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.Service.Services.Repositories.ServiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private ServiceRepositories serviceRepository;

    @Autowired
    private SalonService salonService;

//    @Autowired
//    private AppointmentService appointmentService;


    public Service addService(CreateServiceDto createServiceDto) throws ResourceNotFoundException {

        var salon = salonService.getSalonById(createServiceDto.getSalonId())
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found"));


        Optional<Service> existingService = serviceRepository.findByNameAndSalonId(createServiceDto.getName(), createServiceDto.getSalonId());
        if (existingService.isPresent()) {
            throw new IllegalArgumentException("Service already exists in this salon.");
        }

        Service newService = new Service(
                createServiceDto.getName(),
                createServiceDto.getDescription(),
                createServiceDto.getPrice(),
                createServiceDto.getDuration(),
                salon
        );

        return serviceRepository.save(newService);
    }


    public Service updateService(UUID serviceId, UpdateServiceDto updateServiceDto) throws ResourceNotFoundException {
        Service existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        existingService.setName(updateServiceDto.getName());
        existingService.setDescription(updateServiceDto.getDescription());
        existingService.setPrice(updateServiceDto.getPrice());
        existingService.setDuration(updateServiceDto.getDuration());

        return serviceRepository.save(existingService);
    }


    public List<Service> getAllServices(UUID salonId) {
        return serviceRepository.findAll().stream()
                .filter(service -> service.getSalon().getId().equals(salonId))
                .toList();
    }


    public Service findServiceById(UUID serviceId) throws ResourceNotFoundException {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }


    public void deleteService(UUID serviceId) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        serviceRepository.delete(service);
    }
    public List<Service> getServiceByIds(List<UUID> serviceIds) {
        return serviceRepository.findAllById(serviceIds); // Or implement your logic here
    }
}
