package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRecetaDTO {

    private Long idDetalleReceta;
    
    @NotNull(message = "El ID de la receta médica es obligatorio")
    private Long idReceta;
    
    @NotBlank(message = "El medicamento es obligatorio")
    private String medicamento;
    
    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;
    
    @NotBlank(message = "La frecuencia es obligatoria")
    private String frecuencia;
    
    @NotBlank(message = "La duración es obligatoria")
    private String duracion;
}
