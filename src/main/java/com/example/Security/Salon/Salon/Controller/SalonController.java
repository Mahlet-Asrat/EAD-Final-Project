package com.example.Security.Salon.Salon.Controller;

import com.example.Security.Salon.Salon.Model.Dto.CreateSalonDto;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.baseUrl}/salons")
public class SalonController {

    @Autowired
    private SalonService salonService;


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public Salon createSalon(@RequestBody CreateSalonDto createSalonDto) {
        return salonService.createSalon(createSalonDto);
    }

//    @Secured("OWNER")
//    @PostMapping
//    public Salon AddEmployee(@RequestBody Salon salon) {
//        return salonService.createSalon(salon);
//    }


    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{id}")
    public Salon getSalonById(@PathVariable UUID id) {
        return salonService.getSalonById(id).orElseThrow(() -> new RuntimeException("Salon not found"));
    }


    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public Salon updateSalon(@PathVariable UUID id, @RequestBody Salon salon) {
        return salonService.updateSalon(id, salon);
    }


    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public void deleteSalon(@PathVariable UUID id) {
        salonService.deleteSalon(id);
    }
}

