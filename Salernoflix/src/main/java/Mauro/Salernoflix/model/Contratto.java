package Mauro.Salernoflix.model;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipologiaContratto tipologiaContratto;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    @ManyToOne
    private User dipendenteStipulante;

    @ManyToOne
    private User user;

    @ManyToOne
    private Veicolo veicolo;

}
