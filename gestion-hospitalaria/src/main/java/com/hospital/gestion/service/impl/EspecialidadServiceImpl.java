package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.EspecialidadDTO;
import com.hospital.gestion.model.Especialidad;
import com.hospital.gestion.repository.EspecialidadRepository;
import com.hospital.gestion.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadDTO> obtenerTodasLasEspecialidades() {
        return especialidadRepository.findAll().stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialidadDTO> obtenerEspecialidadPorId(Long id) {
        return especialidadRepository.findById(id)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialidadDTO> obtenerEspecialidadPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre)
                .map(this::convertirAEntity);
    }

    @Override
    public EspecialidadDTO crearEspecialidad(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = convertirADTO(especialidadDTO);
        Especialidad especialidadGuardada = especialidadRepository.save(especialidad);
        return convertirAEntity(especialidadGuardada);
    }

    @Override
    public EspecialidadDTO actualizarEspecialidad(Long id, EspecialidadDTO especialidadDTO) {
        Especialidad especialidadExistente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
        
        especialidadExistente.setNombre(especialidadDTO.getNombre());
        especialidadExistente.setDescripcion(especialidadDTO.getDescripcion());
        
        Especialidad especialidadActualizada = especialidadRepository.save(especialidadExistente);
        return convertirAEntity(especialidadActualizada);
    }

    @Override
    public void eliminarEspecialidad(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }

    private EspecialidadDTO convertirAEntity(Especialidad especialidad) {
        return EspecialidadDTO.builder()
                .idEspecialidad(especialidad.getIdEspecialidad())
                .nombre(especialidad.getNombre())
                .descripcion(especialidad.getDescripcion())
                .build();
    }

    private Especialidad convertirADTO(EspecialidadDTO especialidadDTO) {
        return Especialidad.builder()
                .nombre(especialidadDTO.getNombre())
                .descripcion(especialidadDTO.getDescripcion())
                .build();
    }
}
