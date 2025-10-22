package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizacionDTO {

    private Long idHosp;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;
    
    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long idHabitacion;
    
    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;
    
    private LocalDate fechaAlta;
    
    private String diagnosticoIngreso;
    
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(activo|dado de alta)$", message = "El estado debe ser: activo o dado de alta")
    private String estado;
    
    // Información adicional para mostrar
    private String nombrePaciente;
    private String numeroHabitacion;
    private String tipoHabitacion;
}
