package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.AlimentazioneEnum;
import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Enum.StatoVeicoloEnum;
import Mauro.Salernoflix.dto.Enum.TipologiaVeicolo;
import Mauro.Salernoflix.dto.Requests.VeicoloRequest;
import Mauro.Salernoflix.model.Veicolo;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
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
                .kilometri(veicoloRequest.getKilometri())
                .prezzo(veicoloRequest.getPrezzo())
                .alimentazione(veicoloRequest.getAlimentazione())
                .statoVeicolo(veicoloRequest.getStatoVeicolo())
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

    public List<Veicolo> veicoliFiltrati(int pageSize, int pageNumber, Long annoImmatricolazione, Long cilindrata, Long cavalli, Long kilometri, Long prezzo, TipologiaVeicolo tipologiaVeicolo, AlimentazioneEnum alimentazione, StatoVeicoloEnum statoVeicolo) {
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
        if (Objects.nonNull(alimentazione))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getAlimentazione().equals(alimentazione)
            ).toList();
        if (Objects.nonNull(kilometri))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getKilometri() >= kilometri
            ).toList();
        if (Objects.nonNull(statoVeicolo))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getStatoVeicolo().equals(statoVeicolo)
            ).toList();
        if (Objects.nonNull(prezzo))
            tuttiIVeicoli = tuttiIVeicoli.stream().filter(
                veicolo -> veicolo.getPrezzo() >= prezzo
            ).toList();
        return tuttiIVeicoli.stream()
            .skip((long) pageSize * pageNumber)
            .limit(pageSize)
            .toList();
    }

    public List<String> listaMarche() {
        return veicoloRepository.findMarche();
    }

    public Veicolo patchVeicolo(Long idVeicolo, HashMap<String, Object> request) {
        Veicolo veicolo = veicoloRepository.findById(idVeicolo).orElseThrow(
            () -> new RuntimeException("Veicolo non trovato"));
        request.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Veicolo.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, veicolo, value);
        });
        return veicoloRepository.save(veicolo);
    }

}
