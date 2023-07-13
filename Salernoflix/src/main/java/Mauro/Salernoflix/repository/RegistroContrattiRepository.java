package Mauro.Salernoflix.repository;

import Mauro.Salernoflix.model.RegistroContratti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroContrattiRepository extends JpaRepository<RegistroContratti, Long> {

}
