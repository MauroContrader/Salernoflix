package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByUser_IdAndVeicolo_Id(Long id, Long id1);

}
