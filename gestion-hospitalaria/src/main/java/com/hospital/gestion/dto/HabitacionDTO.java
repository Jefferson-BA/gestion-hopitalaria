package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionDTO {

    private Long idHabitacion;
    
    @NotBlank(message = "El número de habitación es obligatorio")
    private String numero;
    
    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Pattern(regexp = "^(UCI|general|emergencia)$", message = "El tipo debe ser: UCI, general o emergencia")
    private String tipo;
    
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(disponible|ocupada|mantenimiento)$", message = "El estado debe ser: disponible, ocupada o mantenimiento")
    private String estado;
}
