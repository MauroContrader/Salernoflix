package Mauro.Salernoflix.Config;

import Mauro.Salernoflix.model.AttivazioneAccount;
import Mauro.Salernoflix.model.User;
import Mauro.Salernoflix.repository.AttivazioneAccountRepository;
import Mauro.Salernoflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    AttivazioneAccountRepository attivazioneAccountRepository;
    @Autowired
    UserRepository userRepository;

    @Scheduled(fixedRate = (1000 * 60 * 60 *24))
    public void deleteCodiciAttivazione() {
        List<AttivazioneAccount> attivazioniScadute = attivazioneAccountRepository.findAll().stream().filter(
            attivazione -> attivazione.getDataIscrizione().isBefore(LocalDateTime.now().minusWeeks(1))
        ).toList();
        if (!attivazioniScadute.isEmpty()) {
            attivazioneAccountRepository.deleteAll(attivazioniScadute);
            List<User> accountAssociati = new ArrayList<>();
            attivazioniScadute.forEach(
                attivazioneAccount ->
                    accountAssociati.add(
                        attivazioneAccount.getUser()
                    )
            );
            userRepository.deleteAll(accountAssociati);
        }
    }
}
