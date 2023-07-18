package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.model.AttivazioneAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface AttivazioneAccountRepository extends JpaRepository<AttivazioneAccount, Long> {

    long deleteByCodiceDiSicurezza(String codiceDiSicurezza);

    AttivazioneAccount findByCodiceDiSicurezza(String codiceDiSicurezza);
}
