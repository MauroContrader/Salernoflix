package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.model.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@Transactional
public interface VeicoloRepository extends JpaRepository<Veicolo, Long> {

    boolean existsBySeriale(@RequestParam String seriale);

    void deleteBySeriale(String seriale);

    @Query("SELECT DISTINCT v.marca FROM Veicolo v")
    List<String> findMarche();

}
