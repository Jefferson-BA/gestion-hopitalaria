package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "antecedente_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AntecedenteMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntecedente;

    @NotNull(message = "El ID de la historia clínica es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historia", nullable = false)
    private HistoriaClinica historiaClinica;

    @NotBlank(message = "El tipo de antecedente es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipo; // alergias, enfermedades previas, cirugías

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}
