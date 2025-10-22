package com.hospital.gestion.service;

import com.hospital.gestion.dto.CitaDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    
    List<CitaDTO> obtenerTodasLasCitas();
    
    Optional<CitaDTO> obtenerCitaPorId(Long id);
    
    List<CitaDTO> obtenerCitasPorPaciente(Long idPaciente);
    
    List<CitaDTO> obtenerCitasPorMedico(Long idMedico);
    
    List<CitaDTO> obtenerCitasPorFecha(LocalDate fecha);
    
    List<CitaDTO> obtenerCitasPorEstado(String estado);
    
    List<CitaDTO> obtenerCitasPorPacienteYEstado(Long idPaciente, String estado);
    
    List<CitaDTO> obtenerCitasPorMedicoYFecha(Long idMedico, LocalDate fecha);
    
    CitaDTO crearCita(CitaDTO citaDTO);
    
    CitaDTO actualizarCita(Long id, CitaDTO citaDTO);
    
    CitaDTO reprogramarCita(Long id, LocalDate nuevaFecha, String nuevaHora);
    
    CitaDTO cancelarCita(Long id);
    
    CitaDTO marcarCitaComoAtendida(Long id);
    
    void eliminarCita(Long id);
}
