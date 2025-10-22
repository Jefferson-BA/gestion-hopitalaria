package com.hospital.gestion.controller;

import com.hospital.gestion.dto.EspecialidadDTO;
import com.hospital.gestion.service.EspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> obtenerTodasLasEspecialidades() {
        List<EspecialidadDTO> especialidades = especialidadService.obtenerTodasLasEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> obtenerEspecialidadPorId(@PathVariable Long id) {
        return especialidadService.obtenerEspecialidadPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EspecialidadDTO> obtenerEspecialidadPorNombre(@PathVariable String nombre) {
        return especialidadService.obtenerEspecialidadPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) {
        EspecialidadDTO especialidadCreada = especialidadService.crearEspecialidad(especialidadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> actualizarEspecialidad(@PathVariable Long id, @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        try {
            EspecialidadDTO especialidadActualizada = especialidadService.actualizarEspecialidad(id, especialidadDTO);
            return ResponseEntity.ok(especialidadActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Long id) {
        try {
            especialidadService.eliminarEspecialidad(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
