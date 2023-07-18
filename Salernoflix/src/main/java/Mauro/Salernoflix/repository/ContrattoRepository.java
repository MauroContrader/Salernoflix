package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.model.Contratto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContrattoRepository extends JpaRepository<Contratto, Long> {

    List<Contratto> findByTipologiaContratto(TipologiaContratto tipologiaContratto);

}
