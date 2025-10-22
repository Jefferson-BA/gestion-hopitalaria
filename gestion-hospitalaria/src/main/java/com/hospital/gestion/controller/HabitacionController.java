package com.hospital.gestion.controller;

import com.hospital.gestion.dto.HabitacionDTO;
import com.hospital.gestion.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<HabitacionDTO>> obtenerTodasLasHabitaciones() {
        List<HabitacionDTO> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTO> obtenerHabitacionPorId(@PathVariable Long id) {
        return habitacionService.obtenerHabitacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<HabitacionDTO> obtenerHabitacionPorNumero(@PathVariable String numero) {
        return habitacionService.obtenerHabitacionPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<HabitacionDTO>> obtenerHabitacionesPorTipo(@PathVariable String tipo) {
        List<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorTipo(tipo);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<HabitacionDTO>> obtenerHabitacionesPorEstado(@PathVariable String estado) {
        List<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorEstado(estado);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/disponibles/tipo/{tipo}")
    public ResponseEntity<List<HabitacionDTO>> obtenerHabitacionesDisponiblesPorTipo(@PathVariable String tipo) {
        List<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesDisponiblesPorTipo(tipo);
        return ResponseEntity.ok(habitaciones);
    }

    @PostMapping
    public ResponseEntity<HabitacionDTO> crearHabitacion(@Valid @RequestBody HabitacionDTO habitacionDTO) {
        HabitacionDTO habitacionCreada = habitacionService.crearHabitacion(habitacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDTO> actualizarHabitacion(@PathVariable Long id, @Valid @RequestBody HabitacionDTO habitacionDTO) {
        try {
            HabitacionDTO habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacionDTO);
            return ResponseEntity.ok(habitacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<HabitacionDTO> cambiarEstadoHabitacion(@PathVariable Long id, @RequestParam String nuevoEstado) {
        try {
            HabitacionDTO habitacionActualizada = habitacionService.cambiarEstadoHabitacion(id, nuevoEstado);
            return ResponseEntity.ok(habitacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        try {
            habitacionService.eliminarHabitacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
