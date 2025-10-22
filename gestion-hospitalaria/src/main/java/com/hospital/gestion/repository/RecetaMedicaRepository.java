package com.hospital.gestion.repository;

import com.hospital.gestion.model.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Long> {
    
    List<RecetaMedica> findByConsultaIdConsulta(Long idConsulta);
}
