package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.CitaDTO;
import com.hospital.gestion.model.Cita;
import com.hospital.gestion.model.Paciente;
import com.hospital.gestion.model.Medico;
import com.hospital.gestion.repository.CitaRepository;
import com.hospital.gestion.repository.PacienteRepository;
import com.hospital.gestion.repository.MedicoRepository;
import com.hospital.gestion.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerTodasLasCitas() {
        return citaRepository.findAll().stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaDTO> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorPaciente(Long idPaciente) {
        return citaRepository.findByPacienteIdPaciente(idPaciente).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorMedico(Long idMedico) {
        return citaRepository.findByMedicoIdMedico(idMedico).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorEstado(String estado) {
        return citaRepository.findByEstado(estado).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorPacienteYEstado(Long idPaciente, String estado) {
        return citaRepository.findByPacienteIdPacienteAndEstado(idPaciente, estado).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerCitasPorMedicoYFecha(Long idMedico, LocalDate fecha) {
        return citaRepository.findByMedicoIdMedicoAndFecha(idMedico, fecha).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CitaDTO crearCita(CitaDTO citaDTO) {
        Paciente paciente = pacienteRepository.findById(citaDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + citaDTO.getIdPaciente()));
        
        Medico medico = medicoRepository.findById(citaDTO.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + citaDTO.getIdMedico()));
        
        Cita cita = Cita.builder()
                .paciente(paciente)
                .medico(medico)
                .fecha(citaDTO.getFecha())
                .hora(citaDTO.getHora())
                .motivo(citaDTO.getMotivo())
                .estado(citaDTO.getEstado())
                .build();
        
        Cita citaGuardada = citaRepository.save(cita);
        return convertirAEntity(citaGuardada);
    }

    @Override
    public CitaDTO actualizarCita(Long id, CitaDTO citaDTO) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        Paciente paciente = pacienteRepository.findById(citaDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + citaDTO.getIdPaciente()));
        
        Medico medico = medicoRepository.findById(citaDTO.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + citaDTO.getIdMedico()));
        
        citaExistente.setPaciente(paciente);
        citaExistente.setMedico(medico);
        citaExistente.setFecha(citaDTO.getFecha());
        citaExistente.setHora(citaDTO.getHora());
        citaExistente.setMotivo(citaDTO.getMotivo());
        citaExistente.setEstado(citaDTO.getEstado());
        
        Cita citaActualizada = citaRepository.save(citaExistente);
        return convertirAEntity(citaActualizada);
    }

    @Override
    public CitaDTO reprogramarCita(Long id, LocalDate nuevaFecha, String nuevaHora) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        if (!"programada".equals(cita.getEstado())) {
            throw new RuntimeException("Solo se pueden reprogramar citas en estado 'programada'");
        }
        
        cita.setFecha(nuevaFecha);
        cita.setHora(LocalTime.parse(nuevaHora));
        
        Cita citaActualizada = citaRepository.save(cita);
        return convertirAEntity(citaActualizada);
    }

    @Override
    public CitaDTO cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        if ("atendida".equals(cita.getEstado())) {
            throw new RuntimeException("No se puede cancelar una cita ya atendida");
        }
        
        cita.setEstado("cancelada");
        Cita citaActualizada = citaRepository.save(cita);
        return convertirAEntity(citaActualizada);
    }

    @Override
    public CitaDTO marcarCitaComoAtendida(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        if (!"programada".equals(cita.getEstado())) {
            throw new RuntimeException("Solo se pueden marcar como atendidas las citas en estado 'programada'");
        }
        
        cita.setEstado("atendida");
        Cita citaActualizada = citaRepository.save(cita);
        return convertirAEntity(citaActualizada);
    }

    @Override
    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }

    private CitaDTO convertirAEntity(Cita cita) {
        return CitaDTO.builder()
                .idCita(cita.getIdCita())
                .idPaciente(cita.getPaciente().getIdPaciente())
                .idMedico(cita.getMedico().getIdMedico())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .nombrePaciente(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos())
                .nombreMedico(cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos())
                .build();
    }
}
