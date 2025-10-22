package com.hospital.gestion.repository;

import com.hospital.gestion.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    
    Optional<Especialidad> findByNombre(String nombre);
}
