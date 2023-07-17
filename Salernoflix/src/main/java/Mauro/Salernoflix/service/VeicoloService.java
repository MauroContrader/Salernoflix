package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Enum.TipologiaVeicolo;
import Mauro.Salernoflix.dto.Requests.VeicoloRequest;
import Mauro.Salernoflix.model.Veicolo;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VeicoloService {

    @Autowired
    VeicoloRepository veicoloRepository;

    public Veicolo creaVeicolo(VeicoloRequest veicoloRequest) {
        if (!veicoloRepository.existsBySeriale(veicoloRequest.getSeriale())) {
            return veicoloRepository.save(Veicolo
                .builder()
                .nome(veicoloRequest.getNome())
                .marca(veicoloRequest.getMarca())
                .cilindrata(veicoloRequest.getCilindrata())
                .cavalli(veicoloRequest.getCavalli())
                .annoImmatricolazione(veicoloRequest.getAnnoImmatricolazione())
                .seriale(veicoloRequest.getSeriale())
                .tipologiaVeicolo(veicoloRequest.getTipologiaVeicolo())
                .build());
        } else
            throw new RuntimeException("Seriale già presente in archivio.");
    }

    public void eliminaVeicolo(String seriale) {
        if (SalSecurityContext.getPrincipal().getRole().equals(Role.ADMIN))
            veicoloRepository.deleteBySeriale(seriale);
        else
            throw new RuntimeException("Solo un admin può eseguire questa operazione.");
    }

    public List<Veicolo> veicoliFiltrati(int pageSize, int pageNumber, Long annoImmatricolazione, Long cilindrata, Long cavalli, TipologiaVeicolo tipologiaVeicolo) {
        List<Veicolo> tuttiIVeicoli = veicoloRepository.findAll();
        if (Objects.nonNull(annoImmatricolazione))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getAnnoImmatricolazione() >= annoImmatricolazione
            ).toList();
        if (Objects.nonNull(cilindrata))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getCilindrata() >= cilindrata
            ).toList();
        if (Objects.nonNull(cavalli))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getCavalli() >= cavalli
            ).toList();
        if (Objects.nonNull(tipologiaVeicolo))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getTipologiaVeicolo().equals(tipologiaVeicolo)
            ).toList();
        return tuttiIVeicoli.stream()
            .skip((long) pageSize * pageNumber)
            .limit(pageSize)
            .toList();
    }

}
