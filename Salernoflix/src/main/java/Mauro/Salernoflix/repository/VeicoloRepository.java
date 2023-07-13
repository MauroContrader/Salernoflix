package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.model.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface VeicoloRepository extends JpaRepository<Veicolo, Long> {

    boolean existsBySeriale(@RequestParam String seriale);

    long deleteBySeriale(String seriale);



}
