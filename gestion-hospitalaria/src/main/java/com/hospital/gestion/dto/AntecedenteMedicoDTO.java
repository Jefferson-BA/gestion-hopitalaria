package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AntecedenteMedicoDTO {

    private Long idAntecedente;
    
    @NotNull(message = "El ID de la historia clínica es obligatorio")
    private Long idHistoria;
    
    @NotBlank(message = "El tipo de antecedente es obligatorio")
    private String tipo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    private LocalDateTime fechaRegistro;
}
