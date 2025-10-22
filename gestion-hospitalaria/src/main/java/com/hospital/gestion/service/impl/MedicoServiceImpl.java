package com.hospital.gestion.service.impl;

import com.hospital.gestion.dto.MedicoDTO;
import com.hospital.gestion.model.Medico;
import com.hospital.gestion.model.MedicoEspecialidad;
import com.hospital.gestion.repository.MedicoRepository;
import com.hospital.gestion.repository.MedicoEspecialidadRepository;
import com.hospital.gestion.repository.EspecialidadRepository;
import com.hospital.gestion.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final MedicoEspecialidadRepository medicoEspecialidadRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicoDTO> obtenerTodosLosMedicos() {
        return medicoRepository.findAll().stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicoDTO> obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicoDTO> obtenerMedicoPorColegiatura(String colegiatura) {
        return medicoRepository.findByColegiatura(colegiatura)
                .map(this::convertirAEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicoDTO> obtenerMedicosPorEstado(Boolean estado) {
        return medicoRepository.findByEstado(estado).stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicoDTO> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, nombre)
                .stream()
                .map(this::convertirAEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MedicoDTO crearMedico(MedicoDTO medicoDTO) {
        Medico medico = convertirADTO(medicoDTO);
        Medico medicoGuardado = medicoRepository.save(medico);
        return convertirAEntity(medicoGuardado);
    }

    @Override
    public MedicoDTO actualizarMedico(Long id, MedicoDTO medicoDTO) {
        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
        
        medicoExistente.setNombres(medicoDTO.getNombres());
        medicoExistente.setApellidos(medicoDTO.getApellidos());
        medicoExistente.setColegiatura(medicoDTO.getColegiatura());
        medicoExistente.setTelefono(medicoDTO.getTelefono());
        medicoExistente.setCorreo(medicoDTO.getCorreo());
        medicoExistente.setEstado(medicoDTO.getEstado());
        
        Medico medicoActualizado = medicoRepository.save(medicoExistente);
        return convertirAEntity(medicoActualizado);
    }

    @Override
    public void eliminarMedico(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("Médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    @Override
    public MedicoDTO agregarEspecialidad(Long idMedico, Long idEspecialidad) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + idMedico));
        
        if (!especialidadRepository.existsById(idEspecialidad)) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + idEspecialidad);
        }
        
        if (medicoEspecialidadRepository.existsByMedicoIdMedicoAndEspecialidadIdEspecialidad(idMedico, idEspecialidad)) {
            throw new RuntimeException("El médico ya tiene esta especialidad asignada");
        }
        
        MedicoEspecialidad medicoEspecialidad = MedicoEspecialidad.builder()
                .medico(medico)
                .especialidad(especialidadRepository.findById(idEspecialidad).orElseThrow())
                .build();
        
        medicoEspecialidadRepository.save(medicoEspecialidad);
        return convertirAEntity(medico);
    }

    @Override
    public MedicoDTO removerEspecialidad(Long idMedico, Long idEspecialidad) {
        if (!medicoEspecialidadRepository.existsByMedicoIdMedicoAndEspecialidadIdEspecialidad(idMedico, idEspecialidad)) {
            throw new RuntimeException("El médico no tiene esta especialidad asignada");
        }
        
        // Buscar la relación específica para eliminarla
        List<MedicoEspecialidad> medicoEspecialidades = medicoEspecialidadRepository.findByMedicoIdMedico(idMedico);
        MedicoEspecialidad medicoEspecialidad = medicoEspecialidades.stream()
                .filter(me -> me.getEspecialidad().getIdEspecialidad().equals(idEspecialidad))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));
        
        medicoEspecialidadRepository.delete(medicoEspecialidad);
        
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + idMedico));
        
        return convertirAEntity(medico);
    }

    private MedicoDTO convertirAEntity(Medico medico) {
        return MedicoDTO.builder()
                .idMedico(medico.getIdMedico())
                .nombres(medico.getNombres())
                .apellidos(medico.getApellidos())
                .colegiatura(medico.getColegiatura())
                .telefono(medico.getTelefono())
                .correo(medico.getCorreo())
                .estado(medico.getEstado())
                .build();
    }

    private Medico convertirADTO(MedicoDTO medicoDTO) {
        return Medico.builder()
                .nombres(medicoDTO.getNombres())
                .apellidos(medicoDTO.getApellidos())
                .colegiatura(medicoDTO.getColegiatura())
                .telefono(medicoDTO.getTelefono())
                .correo(medicoDTO.getCorreo())
                .estado(medicoDTO.getEstado())
                .build();
    }
}
