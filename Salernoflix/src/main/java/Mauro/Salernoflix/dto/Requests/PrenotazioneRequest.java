package Mauro.Salernoflix.dto.Requests;

import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.model.Veicolo;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneRequest {

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    private Long idCliente;

    private Long idVeicolo;

}
