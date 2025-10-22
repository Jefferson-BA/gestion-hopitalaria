package com.hospital.gestion.repository;

import com.hospital.gestion.model.AntecedenteMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntecedenteMedicoRepository extends JpaRepository<AntecedenteMedico, Long> {
    
    List<AntecedenteMedico> findByHistoriaClinicaIdHistoria(Long idHistoria);
    
    List<AntecedenteMedico> findByTipo(String tipo);
}
