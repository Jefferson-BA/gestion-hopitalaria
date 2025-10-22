package com.hospital.gestion.controller;

import com.hospital.gestion.dto.MedicoDTO;
import com.hospital.gestion.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> obtenerTodosLosMedicos() {
        List<MedicoDTO> medicos = medicoService.obtenerTodosLosMedicos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> obtenerMedicoPorId(@PathVariable Long id) {
        return medicoService.obtenerMedicoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/colegiatura/{colegiatura}")
    public ResponseEntity<MedicoDTO> obtenerMedicoPorColegiatura(@PathVariable String colegiatura) {
        return medicoService.obtenerMedicoPorColegiatura(colegiatura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MedicoDTO>> obtenerMedicosPorEstado(@PathVariable Boolean estado) {
        List<MedicoDTO> medicos = medicoService.obtenerMedicosPorEstado(estado);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MedicoDTO>> buscarMedicosPorNombre(@RequestParam String nombre) {
        List<MedicoDTO> medicos = medicoService.buscarMedicosPorNombre(nombre);
        return ResponseEntity.ok(medicos);
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        MedicoDTO medicoCreado = medicoService.crearMedico(medicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> actualizarMedico(@PathVariable Long id, @Valid @RequestBody MedicoDTO medicoDTO) {
        try {
            MedicoDTO medicoActualizado = medicoService.actualizarMedico(id, medicoDTO);
            return ResponseEntity.ok(medicoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        try {
            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idMedico}/especialidades/{idEspecialidad}")
    public ResponseEntity<MedicoDTO> agregarEspecialidad(@PathVariable Long idMedico, @PathVariable Long idEspecialidad) {
        try {
            MedicoDTO medicoActualizado = medicoService.agregarEspecialidad(idMedico, idEspecialidad);
            return ResponseEntity.ok(medicoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idMedico}/especialidades/{idEspecialidad}")
    public ResponseEntity<MedicoDTO> removerEspecialidad(@PathVariable Long idMedico, @PathVariable Long idEspecialidad) {
        try {
            MedicoDTO medicoActualizado = medicoService.removerEspecialidad(idMedico, idEspecialidad);
            return ResponseEntity.ok(medicoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
