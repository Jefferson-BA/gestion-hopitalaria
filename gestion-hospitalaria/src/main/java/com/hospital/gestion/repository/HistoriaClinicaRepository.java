package com.hospital.gestion.repository;

import com.hospital.gestion.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    
    List<HistoriaClinica> findByPacienteIdPaciente(Long idPaciente);
    
    Optional<HistoriaClinica> findByPacienteIdPacienteAndIdHistoria(Long idPaciente, Long idHistoria);
}
