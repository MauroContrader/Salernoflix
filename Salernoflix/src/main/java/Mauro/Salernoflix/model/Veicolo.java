package Mauro.Salernoflix.model;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.dto.Enum.TipologiaVeicolo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veicolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String marca;

    private String cilindrata;

    private String cavalli;

    private String annoImmatricolazione;

    private String seriale;

    @Enumerated(EnumType.STRING)
    private TipologiaVeicolo tipologiaVeicolo;

}
