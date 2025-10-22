package com.hospital.gestion.repository;

import com.hospital.gestion.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByDni(String dni);

    List<Paciente> findByEstado(Boolean estado);

    List<Paciente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(
            String nombres, String apellidos
    );

    boolean existsByDni(String dni);
}