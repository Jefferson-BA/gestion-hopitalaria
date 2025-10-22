package com.hospital.gestion.service;

import com.hospital.gestion.dto.HabitacionDTO;
import java.util.List;
import java.util.Optional;

public interface HabitacionService {
    
    List<HabitacionDTO> obtenerTodasLasHabitaciones();
    
    Optional<HabitacionDTO> obtenerHabitacionPorId(Long id);
    
    Optional<HabitacionDTO> obtenerHabitacionPorNumero(String numero);
    
    List<HabitacionDTO> obtenerHabitacionesPorTipo(String tipo);
    
    List<HabitacionDTO> obtenerHabitacionesPorEstado(String estado);
    
    List<HabitacionDTO> obtenerHabitacionesDisponiblesPorTipo(String tipo);
    
    HabitacionDTO crearHabitacion(HabitacionDTO habitacionDTO);
    
    HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO);
    
    HabitacionDTO cambiarEstadoHabitacion(Long id, String nuevoEstado);
    
    void eliminarHabitacion(Long id);
}
