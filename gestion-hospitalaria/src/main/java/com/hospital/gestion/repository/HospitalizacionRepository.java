package com.hospital.gestion.repository;

import com.hospital.gestion.model.Hospitalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HospitalizacionRepository extends JpaRepository<Hospitalizacion, Long> {
    
    List<Hospitalizacion> findByPacienteIdPaciente(Long idPaciente);
    
    List<Hospitalizacion> findByHabitacionIdHabitacion(Long idHabitacion);
    
    List<Hospitalizacion> findByEstado(String estado);
    
    List<Hospitalizacion> findByFechaIngreso(LocalDate fechaIngreso);
    
    List<Hospitalizacion> findByPacienteIdPacienteAndEstado(Long idPaciente, String estado);
}
