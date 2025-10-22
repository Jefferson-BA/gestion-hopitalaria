package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticoDTO {

    private Long idDiagnostico;
    
    @NotNull(message = "El ID de la consulta es obligatorio")
    private Long idConsulta;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "^(presuntivo|definitivo)$", message = "El tipo debe ser: presuntivo o definitivo")
    private String tipo;
}
