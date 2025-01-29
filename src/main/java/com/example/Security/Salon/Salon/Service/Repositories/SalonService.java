package com.example.Security.Salon.Salon.Service.Repositories;

import com.example.Security.Salon.Salon.Model.Dto.CreateSalonDto;
import com.example.Security.Salon.Salon.Model.Salon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SalonService {

    @Autowired
    private SalonRepository salonRepository;

    public Salon createSalon(CreateSalonDto createSalonDto) {

        Optional<Salon> existingSalon = salonRepository.findByName(createSalonDto.getName());
        if (existingSalon.isPresent()) {
            throw new RuntimeException("Salon with the name " + createSalonDto.getName() + " already exists.");
        }
        Salon salon = new Salon(
                createSalonDto.getName(),
                createSalonDto.getDescription(),
                createSalonDto.getAddress(),
                createSalonDto.getPhoneNumber(),
                createSalonDto.getOpeningHours(),
                createSalonDto.getImage()

        );

        return salonRepository.save(salon);
    }

    public Optional<Salon> getSalonById(UUID id) {

        return salonRepository.findById(id);
    }

    public Salon updateSalon(UUID id, Salon salonDetails) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new RuntimeException("Salon not found"));
        salon.setName(salonDetails.getName());
        salon.setDescription(salonDetails.getDescription());
        salon.setAddress(salonDetails.getAddress());
        salon.setPhoneNumber(salonDetails.getPhoneNumber());
        salon.setOpeningHours(salonDetails.getOpeningHours());
        salon.setImage(salonDetails.getImage());

        return salonRepository.save(salon);
    }

    public void deleteSalon(UUID id) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new RuntimeException("Salon not found"));
        salonRepository.delete(salon);
    }
}
