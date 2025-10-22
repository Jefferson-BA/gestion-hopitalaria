package com.hospital.gestion.service;

import com.hospital.gestion.dto.MedicoDTO;
import java.util.List;
import java.util.Optional;

public interface MedicoService {
    
    List<MedicoDTO> obtenerTodosLosMedicos();
    
    Optional<MedicoDTO> obtenerMedicoPorId(Long id);
    
    Optional<MedicoDTO> obtenerMedicoPorColegiatura(String colegiatura);
    
    List<MedicoDTO> obtenerMedicosPorEstado(Boolean estado);
    
    List<MedicoDTO> buscarMedicosPorNombre(String nombre);
    
    MedicoDTO crearMedico(MedicoDTO medicoDTO);
    
    MedicoDTO actualizarMedico(Long id, MedicoDTO medicoDTO);
    
    void eliminarMedico(Long id);
    
    MedicoDTO agregarEspecialidad(Long idMedico, Long idEspecialidad);
    
    MedicoDTO removerEspecialidad(Long idMedico, Long idEspecialidad);
}
