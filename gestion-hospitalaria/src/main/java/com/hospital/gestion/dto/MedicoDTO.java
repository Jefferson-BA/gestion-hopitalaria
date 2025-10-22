package com.hospital.gestion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDTO {

    private Long idMedico;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;
    
    @NotBlank(message = "La colegiatura es obligatoria")
    @Size(max = 20)
    private String colegiatura;
    
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;
    
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    private Boolean estado;
    
    private List<EspecialidadDTO> especialidades;
}
