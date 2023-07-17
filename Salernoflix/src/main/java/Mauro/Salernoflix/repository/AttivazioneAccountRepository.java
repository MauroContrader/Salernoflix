package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.model.AttivazioneAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AttivazioneAccountRepository extends JpaRepository<AttivazioneAccount, Long> {


    long deleteByCodiceDiSicurezza(String codiceDiSicurezza);

    AttivazioneAccount findByCodiceDiSicurezza(String codiceDiSicurezza);
}
