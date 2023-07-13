package Mauro.Salernoflix.dto.Requests;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.model.Veicolo;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContrattoRequest {

    private TipologiaContratto tipologiaContratto;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private String codiceFiscaleCliente;

    private Long idVeicolo;

}
