package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadDTO {

    private Long idEspecialidad;
    
    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    @Size(max = 100)
    private String nombre;
    
    @Size(max = 500)
    private String descripcion;
}
