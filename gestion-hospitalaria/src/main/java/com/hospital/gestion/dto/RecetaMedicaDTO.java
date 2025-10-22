package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaMedicaDTO {

    private Long idReceta;
    
    @NotNull(message = "El ID de la consulta es obligatorio")
    private Long idConsulta;
    
    private String indicaciones;
    
    private List<DetalleRecetaDTO> detallesReceta;
}
