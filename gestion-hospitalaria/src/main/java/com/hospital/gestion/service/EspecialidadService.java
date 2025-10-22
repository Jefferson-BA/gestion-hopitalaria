package com.hospital.gestion.service;

import com.hospital.gestion.dto.EspecialidadDTO;
import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    
    List<EspecialidadDTO> obtenerTodasLasEspecialidades();
    
    Optional<EspecialidadDTO> obtenerEspecialidadPorId(Long id);
    
    Optional<EspecialidadDTO> obtenerEspecialidadPorNombre(String nombre);
    
    EspecialidadDTO crearEspecialidad(EspecialidadDTO especialidadDTO);
    
    EspecialidadDTO actualizarEspecialidad(Long id, EspecialidadDTO especialidadDTO);
    
    void eliminarEspecialidad(Long id);
}
