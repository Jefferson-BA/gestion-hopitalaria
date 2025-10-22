package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.HospitalizacionDTO;
import com.hospital.gestion.model.Hospitalizacion;
import com.hospital.gestion.model.Paciente;
import com.hospital.gestion.model.Habitacion;
import com.hospital.gestion.repository.HospitalizacionRepository;
import com.hospital.gestion.repository.PacienteRepository;
import com.hospital.gestion.repository.HabitacionRepository;
import com.hospital.gestion.service.HospitalizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalizacionServiceImpl implements HospitalizacionService {

    private final HospitalizacionRepository hospitalizacionRepository;
    private final PacienteRepository pacienteRepository;
    private final HabitacionRepository habitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerTodasLasHospitalizaciones() {
        return hospitalizacionRepository.findAll().stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HospitalizacionDTO> obtenerHospitalizacionPorId(Long id) {
        return hospitalizacionRepository.findById(id)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerHospitalizacionesPorPaciente(Long idPaciente) {
        return hospitalizacionRepository.findByPacienteIdPaciente(idPaciente).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerHospitalizacionesPorHabitacion(Long idHabitacion) {
        return hospitalizacionRepository.findByHabitacionIdHabitacion(idHabitacion).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerHospitalizacionesPorEstado(String estado) {
        return hospitalizacionRepository.findByEstado(estado).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerHospitalizacionesPorFechaIngreso(LocalDate fechaIngreso) {
        return hospitalizacionRepository.findByFechaIngreso(fechaIngreso).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalizacionDTO> obtenerHospitalizacionesActivasPorPaciente(Long idPaciente) {
        return hospitalizacionRepository.findByPacienteIdPacienteAndEstado(idPaciente, "activo").stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    public HospitalizacionDTO crearHospitalizacion(HospitalizacionDTO hospitalizacionDTO) {
        Paciente paciente = pacienteRepository.findById(hospitalizacionDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + hospitalizacionDTO.getIdPaciente()));
        
        Habitacion habitacion = habitacionRepository.findById(hospitalizacionDTO.getIdHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + hospitalizacionDTO.getIdHabitacion()));
        
        // Verificar que la habitación esté disponible
        if (!"disponible".equals(habitacion.getEstado())) {
            throw new RuntimeException("La habitación no está disponible");
        }
        
        Hospitalizacion hospitalizacion = Hospitalizacion.builder()
                .paciente(paciente)
                .habitacion(habitacion)
                .fechaIngreso(hospitalizacionDTO.getFechaIngreso())
                .fechaAlta(hospitalizacionDTO.getFechaAlta())
                .diagnosticoIngreso(hospitalizacionDTO.getDiagnosticoIngreso())
                .estado(hospitalizacionDTO.getEstado())
                .build();
        
        Hospitalizacion hospitalizacionGuardada = hospitalizacionRepository.save(hospitalizacion);
        
        // Cambiar el estado de la habitación a ocupada
        habitacion.setEstado("ocupada");
        habitacionRepository.save(habitacion);
        
        return convertirAEntity(hospitalizacionGuardada);
    }

    @Override
    public HospitalizacionDTO actualizarHospitalizacion(Long id, HospitalizacionDTO hospitalizacionDTO) {
        Hospitalizacion hospitalizacionExistente = hospitalizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada con ID: " + id));
        
        Paciente paciente = pacienteRepository.findById(hospitalizacionDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + hospitalizacionDTO.getIdPaciente()));
        
        Habitacion habitacion = habitacionRepository.findById(hospitalizacionDTO.getIdHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + hospitalizacionDTO.getIdHabitacion()));
        
        hospitalizacionExistente.setPaciente(paciente);
        hospitalizacionExistente.setHabitacion(habitacion);
        hospitalizacionExistente.setFechaIngreso(hospitalizacionDTO.getFechaIngreso());
        hospitalizacionExistente.setFechaAlta(hospitalizacionDTO.getFechaAlta());
        hospitalizacionExistente.setDiagnosticoIngreso(hospitalizacionDTO.getDiagnosticoIngreso());
        hospitalizacionExistente.setEstado(hospitalizacionDTO.getEstado());
        
        Hospitalizacion hospitalizacionActualizada = hospitalizacionRepository.save(hospitalizacionExistente);
        return convertirAEntity(hospitalizacionActualizada);
    }

    @Override
    public HospitalizacionDTO darAltaPaciente(Long id, LocalDate fechaAlta) {
        Hospitalizacion hospitalizacion = hospitalizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada con ID: " + id));
        
        if (!"activo".equals(hospitalizacion.getEstado())) {
            throw new RuntimeException("Solo se puede dar de alta a pacientes con estado 'activo'");
        }
        
        hospitalizacion.setFechaAlta(fechaAlta);
        hospitalizacion.setEstado("dado de alta");
        
        Hospitalizacion hospitalizacionActualizada = hospitalizacionRepository.save(hospitalizacion);
        
        // Liberar la habitación
        Habitacion habitacion = hospitalizacion.getHabitacion();
        habitacion.setEstado("disponible");
        habitacionRepository.save(habitacion);
        
        return convertirAEntity(hospitalizacionActualizada);
    }

    @Override
    public void eliminarHospitalizacion(Long id) {
        Hospitalizacion hospitalizacion = hospitalizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalización no encontrada con ID: " + id));
        
        // Liberar la habitación si está activa
        if ("activo".equals(hospitalizacion.getEstado())) {
            Habitacion habitacion = hospitalizacion.getHabitacion();
            habitacion.setEstado("disponible");
            habitacionRepository.save(habitacion);
        }
        
        hospitalizacionRepository.deleteById(id);
    }

    private HospitalizacionDTO convertirAEntity(Hospitalizacion hospitalizacion) {
        return HospitalizacionDTO.builder()
                .idHosp(hospitalizacion.getIdHosp())
                .idPaciente(hospitalizacion.getPaciente().getIdPaciente())
                .idHabitacion(hospitalizacion.getHabitacion().getIdHabitacion())
                .fechaIngreso(hospitalizacion.getFechaIngreso())
                .fechaAlta(hospitalizacion.getFechaAlta())
                .diagnosticoIngreso(hospitalizacion.getDiagnosticoIngreso())
                .estado(hospitalizacion.getEstado())
                .nombrePaciente(hospitalizacion.getPaciente().getNombres() + " " + hospitalizacion.getPaciente().getApellidos())
                .numeroHabitacion(hospitalizacion.getHabitacion().getNumero())
                .tipoHabitacion(hospitalizacion.getHabitacion().getTipo())
                .build();
    }
}
