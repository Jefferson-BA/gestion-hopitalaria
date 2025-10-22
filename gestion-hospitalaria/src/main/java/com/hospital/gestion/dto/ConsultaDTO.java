package com.hospital.gestion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaDTO {

    private Long idConsulta;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Long idCita;
    
    @NotNull(message = "El ID del médico es obligatorio")
    private Long idMedico;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
    
    private String motivoConsulta;
    
    private String observaciones;
    
    private List<DiagnosticoDTO> diagnosticos;
    
    private List<RecetaMedicaDTO> recetasMedicas;
    
    // Información adicional para mostrar
    private String nombrePaciente;
    private String nombreMedico;
}
