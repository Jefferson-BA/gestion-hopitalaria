package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "habitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabitacion;

    @NotBlank(message = "El número de habitación es obligatorio")
    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Pattern(regexp = "^(UCI|general|emergencia)$", message = "El tipo debe ser: UCI, general o emergencia")
    @Column(nullable = false, length = 20)
    private String tipo;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(disponible|ocupada|mantenimiento)$", message = "El estado debe ser: disponible, ocupada o mantenimiento")
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String estado = "disponible";

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hospitalizacion> hospitalizaciones;
}
