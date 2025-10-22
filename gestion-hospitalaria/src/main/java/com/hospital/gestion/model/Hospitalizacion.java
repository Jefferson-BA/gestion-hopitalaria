package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "hospitalizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHosp;

    @NotNull(message = "El ID del paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @NotNull(message = "El ID de la habitación es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Habitacion habitacion;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaIngreso;

    private LocalDate fechaAlta;

    @Column(length = 500)
    private String diagnosticoIngreso;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(activo|dado de alta)$", message = "El estado debe ser: activo o dado de alta")
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String estado = "activo";
}
