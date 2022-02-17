package hu.progmatic.felhasznalo.token;

import hu.progmatic.felhasznalo.Felhasznalo;
import hu.progmatic.felhasznalo.FelhasznaloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class MegerositoTokenService {

    @Autowired
    private MegerositoTokenRepository megerositoTokenRepository;
    @Autowired
    private FelhasznaloRepository felhasznaloRepository;

    public void megerosites(String token) {
        MegerositoToken megerositoToken = megerositoTokenRepository.findByToken(token).orElseThrow(() -> new NemLetezoTokenException("Nemlétező token!"));
        Felhasznalo felhasznalo = felhasznaloRepository.getById(megerositoToken.getFelhasznalo().getId());
        felhasznalo.setEngedelyezve(true);
        felhasznalo.setMegerositesIdeje(LocalDateTime.now());
        megerositoTokenRepository.delete(megerositoToken);

    }
}
