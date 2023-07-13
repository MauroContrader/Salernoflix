package Mauro.Salernoflix.dto.Requests;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnagraficaUtenteRequest {

    private String nome;

    private String cognome;

    private String citta;

    private String codiceFiscale;

    private String indirizzo;

    private String email;

    private String cellulare;

}