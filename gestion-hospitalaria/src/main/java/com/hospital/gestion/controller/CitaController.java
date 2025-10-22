package com.hospital.gestion.controller;

import com.hospital.gestion.dto.CitaDTO;
import com.hospital.gestion.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> obtenerTodasLasCitas() {
        List<CitaDTO> citas = citaService.obtenerTodasLasCitas();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> obtenerCitaPorId(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorPaciente(@PathVariable Long idPaciente) {
        List<CitaDTO> citas = citaService.obtenerCitasPorPaciente(idPaciente);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorMedico(@PathVariable Long idMedico) {
        List<CitaDTO> citas = citaService.obtenerCitasPorMedico(idMedico);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<CitaDTO> citas = citaService.obtenerCitasPorFecha(fecha);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorEstado(@PathVariable String estado) {
        List<CitaDTO> citas = citaService.obtenerCitasPorEstado(estado);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/paciente/{idPaciente}/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorPacienteYEstado(@PathVariable Long idPaciente, @PathVariable String estado) {
        List<CitaDTO> citas = citaService.obtenerCitasPorPacienteYEstado(idPaciente, estado);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/medico/{idMedico}/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorMedicoYFecha(@PathVariable Long idMedico, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<CitaDTO> citas = citaService.obtenerCitasPorMedicoYFecha(idMedico, fecha);
        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crearCita(@Valid @RequestBody CitaDTO citaDTO) {
        CitaDTO citaCreada = citaService.crearCita(citaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Long id, @Valid @RequestBody CitaDTO citaDTO) {
        try {
            CitaDTO citaActualizada = citaService.actualizarCita(id, citaDTO);
            return ResponseEntity.ok(citaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<CitaDTO> reprogramarCita(@PathVariable Long id, 
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFecha,
                                                   @RequestParam String nuevaHora) {
        try {
            CitaDTO citaReprogramada = citaService.reprogramarCita(id, nuevaFecha, nuevaHora);
            return ResponseEntity.ok(citaReprogramada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CitaDTO> cancelarCita(@PathVariable Long id) {
        try {
            CitaDTO citaCancelada = citaService.cancelarCita(id);
            return ResponseEntity.ok(citaCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/atendida")
    public ResponseEntity<CitaDTO> marcarCitaComoAtendida(@PathVariable Long id) {
        try {
            CitaDTO citaAtendida = citaService.marcarCitaComoAtendida(id);
            return ResponseEntity.ok(citaAtendida);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        try {
            citaService.eliminarCita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
