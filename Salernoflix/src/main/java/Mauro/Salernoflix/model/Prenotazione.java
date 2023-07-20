package Mauro.Salernoflix.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInizio;

    private LocalDateTime dataFine;

    @ManyToOne
    private User user;

    @ManyToOne
    private Veicolo veicolo;

    @ManyToOne
    private User operatore;

}
