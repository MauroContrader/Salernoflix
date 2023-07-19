package Mauro.Salernoflix.model;

import Mauro.Salernoflix.dto.Enum.AlimentazioneEnum;
import Mauro.Salernoflix.dto.Enum.StatoVeicoloEnum;
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

    private Long cilindrata;

    private Long cavalli;

    private Long annoImmatricolazione;

    private String seriale;

    private Long kilometri;

    private Long prezzo;

    @Enumerated(EnumType.STRING)
    private AlimentazioneEnum alimentazione;

    @Enumerated(EnumType.STRING)
    private StatoVeicoloEnum statoVeicolo;

    @Enumerated(EnumType.STRING)
    private TipologiaVeicolo tipologiaVeicolo;

}
