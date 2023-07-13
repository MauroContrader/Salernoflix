package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Requests.ContrattoRequest;
import Mauro.Salernoflix.model.RegistroContratti;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.repository.RegistroContrattiRepository;
import Mauro.Salernoflix.repository.UserRepository;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroContrattiService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegistroContrattiRepository registroContrattiRepository;

    @Autowired
    VeicoloRepository veicoloRepository;

    public RegistroContratti inserisciContratto(ContrattoRequest contrattoRequest) {
        User user = userRepository.findById(SalSecurityContext.getPrincipal().getId())
            .orElseThrow(() -> new RuntimeException("Utente " + SalSecurityContext.getPrincipal().getUsername() + "non presente in piattaforma."));
        if (user.getRole().equals(Role.ADMIN)) {
            return registroContrattiRepository.save(
                RegistroContratti.builder()
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

}
