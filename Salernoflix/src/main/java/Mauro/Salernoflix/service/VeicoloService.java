package Mauro.Salernoflix.service;

import Mauro.Salernoflix.dto.Enum.Role;
import Mauro.Salernoflix.dto.Requests.VeicoloRequest;
import Mauro.Salernoflix.model.Veicolo;
import Mauro.Salernoflix.repository.VeicoloRepository;
import Mauro.Salernoflix.security.SalSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
