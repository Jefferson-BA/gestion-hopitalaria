package com.hospital.gestion.repository;

import com.hospital.gestion.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByPacienteIdPaciente(Long idPaciente);
    
    List<Consulta> findByMedicoIdMedico(Long idMedico);
    
    List<Consulta> findByFecha(LocalDate fecha);
    
    List<Consulta> findByPacienteIdPacienteAndFecha(Long idPaciente, LocalDate fecha);
    
    List<Consulta> findByMedicoIdMedicoAndFecha(Long idMedico, LocalDate fecha);
}
