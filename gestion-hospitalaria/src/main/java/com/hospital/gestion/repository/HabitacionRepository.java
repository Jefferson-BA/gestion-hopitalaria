package com.hospital.gestion.repository;

import com.hospital.gestion.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    
    Optional<Habitacion> findByNumero(String numero);
    
    List<Habitacion> findByTipo(String tipo);
    
    List<Habitacion> findByEstado(String estado);
    
    List<Habitacion> findByTipoAndEstado(String tipo, String estado);
}
