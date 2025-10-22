package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "detalle_receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleReceta;

    @NotNull(message = "El ID de la receta médica es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private RecetaMedica recetaMedica;

    @NotBlank(message = "El medicamento es obligatorio")
    @Column(nullable = false, length = 200)
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria")
    @Column(nullable = false, length = 100)
    private String dosis;

    @NotBlank(message = "La frecuencia es obligatoria")
    @Column(nullable = false, length = 100)
    private String frecuencia;

    @NotBlank(message = "La duración es obligatoria")
    @Column(nullable = false, length = 100)
    private String duracion;
}
