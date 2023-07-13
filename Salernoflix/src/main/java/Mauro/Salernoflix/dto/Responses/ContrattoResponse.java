package Mauro.Salernoflix.dto.Responses;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.model.Veicolo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContrattoResponse {

    @Enumerated(EnumType.STRING)
    private TipologiaContratto tipologiaContratto;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private Long idDipendenteStipulante;

    private Long idCliente;

    private Long idVeicolo;

}
