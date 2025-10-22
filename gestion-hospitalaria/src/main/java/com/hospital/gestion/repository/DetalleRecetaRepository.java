package com.hospital.gestion.repository;

import com.hospital.gestion.model.DetalleReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
    
    List<DetalleReceta> findByRecetaMedicaIdReceta(Long idReceta);
}
