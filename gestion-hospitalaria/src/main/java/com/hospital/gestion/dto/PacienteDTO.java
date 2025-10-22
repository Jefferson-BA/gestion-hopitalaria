package com.hospital.gestion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDTO {

    private Long idPaciente;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(M|F)$")
    private String sexo;

    @Size(max = 200)
    private String direccion;

    @Pattern(regexp = "^$|^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos o estar vacío")
    private String telefono;

    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$", message = "El correo debe ser válido o estar vacío")
    private String correo;

    private Boolean estado;
}