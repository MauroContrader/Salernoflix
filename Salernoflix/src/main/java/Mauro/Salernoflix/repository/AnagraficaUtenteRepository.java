package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.model.AnagraficaUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnagraficaUtenteRepository extends JpaRepository<AnagraficaUtente,Long> {

}
