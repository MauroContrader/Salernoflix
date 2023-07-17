package Mauro.Salernoflix.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AttivazioneAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codiceDiSicurezza;

    private LocalDateTime dataIscrizione;

    @OneToOne
    private User user;

}
