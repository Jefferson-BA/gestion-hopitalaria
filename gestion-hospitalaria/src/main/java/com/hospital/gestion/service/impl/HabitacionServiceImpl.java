package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.HabitacionDTO;
import com.hospital.gestion.model.Habitacion;
import com.hospital.gestion.repository.HabitacionRepository;
import com.hospital.gestion.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionDTO> obtenerTodasLasHabitaciones() {
        return habitacionRepository.findAll().stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitacionDTO> obtenerHabitacionPorId(Long id) {
        return habitacionRepository.findById(id)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitacionDTO> obtenerHabitacionPorNumero(String numero) {
        return habitacionRepository.findByNumero(numero)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionDTO> obtenerHabitacionesPorTipo(String tipo) {
        return habitacionRepository.findByTipo(tipo).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionDTO> obtenerHabitacionesPorEstado(String estado) {
        return habitacionRepository.findByEstado(estado).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionDTO> obtenerHabitacionesDisponiblesPorTipo(String tipo) {
        return habitacionRepository.findByTipoAndEstado(tipo, "disponible").stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    public HabitacionDTO crearHabitacion(HabitacionDTO habitacionDTO) {
        Habitacion habitacion = convertirADTO(habitacionDTO);
        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);
        return convertirAEntity(habitacionGuardada);
    }

    @Override
    public HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO) {
        Habitacion habitacionExistente = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
        
        habitacionExistente.setNumero(habitacionDTO.getNumero());
        habitacionExistente.setTipo(habitacionDTO.getTipo());
        habitacionExistente.setEstado(habitacionDTO.getEstado());
        
        Habitacion habitacionActualizada = habitacionRepository.save(habitacionExistente);
        return convertirAEntity(habitacionActualizada);
    }

    @Override
    public HabitacionDTO cambiarEstadoHabitacion(Long id, String nuevoEstado) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
        
        habitacion.setEstado(nuevoEstado);
        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);
        return convertirAEntity(habitacionActualizada);
    }

    @Override
    public void eliminarHabitacion(Long id) {
        if (!habitacionRepository.existsById(id)) {
            throw new RuntimeException("Habitación no encontrada con ID: " + id);
        }
        habitacionRepository.deleteById(id);
    }

    private HabitacionDTO convertirAEntity(Habitacion habitacion) {
        return HabitacionDTO.builder()
                .idHabitacion(habitacion.getIdHabitacion())
                .numero(habitacion.getNumero())
                .tipo(habitacion.getTipo())
                .estado(habitacion.getEstado())
                .build();
    }

    private Habitacion convertirADTO(HabitacionDTO habitacionDTO) {
        return Habitacion.builder()
                .numero(habitacionDTO.getNumero())
                .tipo(habitacionDTO.getTipo())
                .estado(habitacionDTO.getEstado())
                .build();
    }
}
