package com.hospital.gestion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {

    private Long idCita;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;
    
    @NotNull(message = "El ID del médico es obligatorio")
    private Long idMedico;
    
    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
    
    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 200)
    private String motivo;
    
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(programada|atendida|cancelada)$", message = "El estado debe ser: programada, atendida o cancelada")
    private String estado;
    
    // Información adicional para mostrar
    private String nombrePaciente;
    private String nombreMedico;
}
