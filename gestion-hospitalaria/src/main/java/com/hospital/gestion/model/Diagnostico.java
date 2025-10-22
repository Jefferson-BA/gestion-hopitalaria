package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "diagnostico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiagnostico;

    @NotNull(message = "El ID de la consulta es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "^(presuntivo|definitivo)$", message = "El tipo debe ser: presuntivo o definitivo")
    @Column(nullable = false, length = 20)
    private String tipo;
}
