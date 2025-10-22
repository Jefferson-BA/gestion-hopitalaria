package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.PacienteDTO;
import com.hospital.gestion.model.Paciente;
import com.hospital.gestion.repository.PacienteRepository;
import com.hospital.gestion.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    public PacienteDTO crearPaciente(PacienteDTO pacienteDTO) {
        if (pacienteRepository.existsByDni(pacienteDTO.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + pacienteDTO.getDni());
        }

        Paciente paciente = convertirDTOaEntidad(pacienteDTO);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return convertirEntidadaDTO(pacienteGuardado);
    }

    @Override
    @Transactional
    public PacienteDTO actualizarPaciente(Long id, PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));

        paciente.setNombres(pacienteDTO.getNombres());
        paciente.setApellidos(pacienteDTO.getApellidos());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setSexo(pacienteDTO.getSexo());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setCorreo(pacienteDTO.getCorreo());

        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        return convertirEntidadaDTO(pacienteActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteDTO obtenerPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
        return convertirEntidadaDTO(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteDTO obtenerPacientePorDni(String dni) {
        Paciente paciente = pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
        return convertirEntidadaDTO(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> listarTodosPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::convertirEntidadaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> listarPacientesActivos() {
        return pacienteRepository.findByEstado(true)
                .stream()
                .map(this::convertirEntidadaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> buscarPacientes(String busqueda) {
        return pacienteRepository
                .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(busqueda, busqueda)
                .stream()
                .map(this::convertirEntidadaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void desactivarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
        paciente.setEstado(false);
        pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    // Métodos de conversión
    private PacienteDTO convertirEntidadaDTO(Paciente paciente) {
        return PacienteDTO.builder()
                .idPaciente(paciente.getIdPaciente())
                .dni(paciente.getDni())
                .nombres(paciente.getNombres())
                .apellidos(paciente.getApellidos())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .sexo(paciente.getSexo())
                .direccion(paciente.getDireccion())
                .telefono(paciente.getTelefono())
                .correo(paciente.getCorreo())
                .estado(paciente.getEstado())
                .build();
    }

    private Paciente convertirDTOaEntidad(PacienteDTO dto) {
        return Paciente.builder()
                .dni(dto.getDni())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .fechaNacimiento(dto.getFechaNacimiento())
                .sexo(dto.getSexo())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();
    }
}