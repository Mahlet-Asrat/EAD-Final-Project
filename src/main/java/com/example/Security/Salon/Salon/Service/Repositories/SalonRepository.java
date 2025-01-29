package com.example.Security.Salon.Salon.Service.Repositories;

import com.example.Security.Salon.Salon.Model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SalonRepository extends JpaRepository<Salon, UUID> {
    Optional<Salon> findByName(String name);
}
