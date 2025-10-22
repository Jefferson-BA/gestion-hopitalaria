package com.hospital.gestion.controller;

import com.hospital.gestion.dto.PacienteDTO;
import com.hospital.gestion.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        try {
            PacienteDTO nuevoPaciente = pacienteService.crearPaciente(pacienteDTO);
            return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodosPacientes() {
        List<PacienteDTO> pacientes = pacienteService.listarTodosPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PacienteDTO>> listarPacientesActivos() {
        List<PacienteDTO> pacientes = pacienteService.listarPacientesActivos();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPacientePorId(@PathVariable Long id) {
        try {
            PacienteDTO paciente = pacienteService.obtenerPacientePorId(id);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> obtenerPacientePorDni(@PathVariable String dni) {
        try {
            PacienteDTO paciente = pacienteService.obtenerPacientePorDni(dni);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteDTO>> buscarPacientes(@RequestParam String q) {
        List<PacienteDTO> pacientes = pacienteService.buscarPacientes(q);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteDTO pacienteDTO) {
        try {
            PacienteDTO pacienteActualizado = pacienteService.actualizarPaciente(id, pacienteDTO);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarPaciente(@PathVariable Long id) {
        try {
            pacienteService.desactivarPaciente(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Paciente desactivado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        try {
            pacienteService.eliminarPaciente(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Paciente eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}