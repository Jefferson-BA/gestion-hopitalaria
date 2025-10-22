package com.hospital.gestion.service;

import com.hospital.gestion.dto.PacienteDTO;
import java.util.List;

public interface PacienteService {

    PacienteDTO crearPaciente(PacienteDTO pacienteDTO);

    PacienteDTO actualizarPaciente(Long id, PacienteDTO pacienteDTO);

    PacienteDTO obtenerPacientePorId(Long id);

    PacienteDTO obtenerPacientePorDni(String dni);

    List<PacienteDTO> listarTodosPacientes();

    List<PacienteDTO> listarPacientesActivos();

    List<PacienteDTO> buscarPacientes(String busqueda);

    void desactivarPaciente(Long id);

    void eliminarPaciente(Long id);
}