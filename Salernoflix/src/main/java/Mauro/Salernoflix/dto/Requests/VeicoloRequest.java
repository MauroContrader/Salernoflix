package Mauro.Salernoflix.dto.Requests;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.dto.Enum.TipologiaVeicolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeicoloRequest {

    private String nome;

    private String marca;

    private Long cilindrata;

    private Long cavalli;

    private Long annoImmatricolazione;

    private String seriale;

    private TipologiaVeicolo tipologiaVeicolo;

}
