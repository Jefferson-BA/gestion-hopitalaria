package com.hospital.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "receta_medica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReceta;

    @NotNull(message = "El ID de la consulta es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @Column(length = 1000)
    private String indicaciones;

    @OneToMany(mappedBy = "recetaMedica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleReceta> detallesReceta;
}
