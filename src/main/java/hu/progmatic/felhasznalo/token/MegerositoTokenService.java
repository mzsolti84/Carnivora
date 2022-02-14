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


    public MegerositoToken save(MegerositoToken megerositoToken){
        return megerositoTokenRepository.save(megerositoToken);
    }

    public MegerositoToken getByToken(String token){
        return megerositoTokenRepository.findByToken(token).orElseThrow();
    }

    public void megerosites(String token) {
        MegerositoToken megerositoToken = megerositoTokenRepository.findByToken(token).orElseThrow(()-> new NemLetezoTokenException("Nemlétező token!"));
        if (megerositoToken.getLejaratDatuma().isAfter(LocalDateTime.now())){
            Felhasznalo felhasznalo = felhasznaloRepository.getById(megerositoToken.getFelhasznalo().getId());
            felhasznalo.setEngedelyezve(true);
            megerositoTokenRepository.delete(megerositoToken);
        }else{
            throw new LejartTokenException("A megerősító link már nem érvényes!");
        }
    }
}
