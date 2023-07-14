package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Enum.TipologiaContratto;
import Mauro.Salernoflix.dto.Requests.ContrattoRequest;
import Mauro.Salernoflix.dto.Responses.ContrattoResponse;
import Mauro.Salernoflix.model.Contratto;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.repository.ContrattoRepository;
import Mauro.Salernoflix.repository.UserRepository;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContrattiService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContrattoRepository contrattoRepository;

    @Autowired
    VeicoloRepository veicoloRepository;

    public Contratto inserisciContratto(ContrattoRequest contrattoRequest) {
        User user = userRepository.findById(SalSecurityContext.getPrincipal().getId())
            .orElseThrow(() -> new RuntimeException("Utente " + SalSecurityContext.getPrincipal().getUsername() + "non presente in piattaforma."));
        if (user.getRole().equals(Role.ADMIN)) {
            return contrattoRepository.save(
                Contratto.builder()
                    .tipologiaContratto(contrattoRequest.getTipologiaContratto())
                    .dataInizio(contrattoRequest.getDataInizio())
                    .dataFine(contrattoRequest.getDataFine())
                    .dipendenteStipulante(userRepository.findByUsername(SalSecurityContext.getPrincipal().getUsername()))
                    .user(userRepository.findByAnagraficaUtente_CodiceFiscale(contrattoRequest.getCodiceFiscaleCliente()))
                    .veicolo(veicoloRepository.findById(contrattoRequest.getIdVeicolo()).orElseThrow(
                        () -> new RuntimeException("Id veicolo non presente in archivio.")
                    ))
                    .build()
            );
        } else
            throw new RuntimeException("Utente non abilitato alla stipula contrattuale.");

    }

    public void eliminaContratto(Long idContratto) {

        if (SalSecurityContext.getPrincipal().getRole().equals(Role.ADMIN)) {
            contrattoRepository.deleteById(idContratto);
        } else
            throw new RuntimeException("Utente non abilitato all'eliminazione dei contratti.");

    }

    public List<ContrattoResponse> visualizzaContrattiByTipologia(TipologiaContratto tipologiaContratto, int pageSize, int pageNumber) {
        List<Contratto> contratti = contrattoRepository.findByTipologiaContratto(tipologiaContratto);
        List<ContrattoResponse> response = new ArrayList<>();
        contratti.forEach(contratto ->
                response.add(
                    ContrattoResponse.builder()
                    .tipologiaContratto(contratto.getTipologiaContratto())
                    .dataInizio(contratto.getDataInizio())
                    .dataFine(contratto.getDataFine())
                    .idDipendenteStipulante(contratto.getDipendenteStipulante().getId())
                    .idCliente(contratto.getUser().getId())
                    .idVeicolo(contratto.getVeicolo().getId())
                    .build())
            );
        return response.stream()
            .skip((long) pageSize * pageNumber)
            .limit(pageSize)
            .toList();
    }

}
