package com.hospital.gestion.service;

import com.hospital.gestion.dto.HospitalizacionDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HospitalizacionService {
    
    List<HospitalizacionDTO> obtenerTodasLasHospitalizaciones();
    
    Optional<HospitalizacionDTO> obtenerHospitalizacionPorId(Long id);
    
    List<HospitalizacionDTO> obtenerHospitalizacionesPorPaciente(Long idPaciente);
    
    List<HospitalizacionDTO> obtenerHospitalizacionesPorHabitacion(Long idHabitacion);
    
    List<HospitalizacionDTO> obtenerHospitalizacionesPorEstado(String estado);
    
    List<HospitalizacionDTO> obtenerHospitalizacionesPorFechaIngreso(LocalDate fechaIngreso);
    
    List<HospitalizacionDTO> obtenerHospitalizacionesActivasPorPaciente(Long idPaciente);
    
    HospitalizacionDTO crearHospitalizacion(HospitalizacionDTO hospitalizacionDTO);
    
    HospitalizacionDTO actualizarHospitalizacion(Long id, HospitalizacionDTO hospitalizacionDTO);
    
    HospitalizacionDTO darAltaPaciente(Long id, LocalDate fechaAlta);
    
    void eliminarHospitalizacion(Long id);
}
