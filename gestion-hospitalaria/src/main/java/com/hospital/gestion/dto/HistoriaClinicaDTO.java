package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinicaDTO {

    private Long idHistoria;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;
    
    @NotNull(message = "La fecha de apertura es obligatoria")
    private LocalDateTime fechaApertura;
    
    private String observaciones;
    
    private List<AntecedenteMedicoDTO> antecedentesMedicos;
}
