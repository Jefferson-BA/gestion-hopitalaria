package com.hospital.gestion.controller;

import com.hospital.gestion.dto.HospitalizacionDTO;
import com.hospital.gestion.service.HospitalizacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hospitalizaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HospitalizacionController {

    private final HospitalizacionService hospitalizacionService;

    @GetMapping
    public ResponseEntity<List<HospitalizacionDTO>> obtenerTodasLasHospitalizaciones() {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerTodasLasHospitalizaciones();
        return ResponseEntity.ok(hospitalizaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalizacionDTO> obtenerHospitalizacionPorId(@PathVariable Long id) {
        return hospitalizacionService.obtenerHospitalizacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerHospitalizacionesPorPaciente(@PathVariable Long idPaciente) {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerHospitalizacionesPorPaciente(idPaciente);
        return ResponseEntity.ok(hospitalizaciones);
    }

    @GetMapping("/habitacion/{idHabitacion}")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerHospitalizacionesPorHabitacion(@PathVariable Long idHabitacion) {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerHospitalizacionesPorHabitacion(idHabitacion);
        return ResponseEntity.ok(hospitalizaciones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerHospitalizacionesPorEstado(@PathVariable String estado) {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerHospitalizacionesPorEstado(estado);
        return ResponseEntity.ok(hospitalizaciones);
    }

    @GetMapping("/fecha-ingreso/{fechaIngreso}")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerHospitalizacionesPorFechaIngreso(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIngreso) {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerHospitalizacionesPorFechaIngreso(fechaIngreso);
        return ResponseEntity.ok(hospitalizaciones);
    }

    @GetMapping("/paciente/{idPaciente}/activas")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerHospitalizacionesActivasPorPaciente(@PathVariable Long idPaciente) {
        List<HospitalizacionDTO> hospitalizaciones = hospitalizacionService.obtenerHospitalizacionesActivasPorPaciente(idPaciente);
        return ResponseEntity.ok(hospitalizaciones);
    }

    @PostMapping
    public ResponseEntity<HospitalizacionDTO> crearHospitalizacion(@Valid @RequestBody HospitalizacionDTO hospitalizacionDTO) {
        try {
            HospitalizacionDTO hospitalizacionCreada = hospitalizacionService.crearHospitalizacion(hospitalizacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(hospitalizacionCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalizacionDTO> actualizarHospitalizacion(@PathVariable Long id, @Valid @RequestBody HospitalizacionDTO hospitalizacionDTO) {
        try {
            HospitalizacionDTO hospitalizacionActualizada = hospitalizacionService.actualizarHospitalizacion(id, hospitalizacionDTO);
            return ResponseEntity.ok(hospitalizacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/alta")
    public ResponseEntity<HospitalizacionDTO> darAltaPaciente(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAlta) {
        try {
            HospitalizacionDTO hospitalizacionActualizada = hospitalizacionService.darAltaPaciente(id, fechaAlta);
            return ResponseEntity.ok(hospitalizacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHospitalizacion(@PathVariable Long id) {
        try {
            hospitalizacionService.eliminarHospitalizacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
