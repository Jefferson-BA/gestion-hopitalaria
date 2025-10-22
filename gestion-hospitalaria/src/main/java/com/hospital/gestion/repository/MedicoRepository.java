package com.hospital.gestion.repository;

import com.hospital.gestion.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    Optional<Medico> findByColegiatura(String colegiatura);
    
    List<Medico> findByEstado(Boolean estado);
    
    List<Medico> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);
}
