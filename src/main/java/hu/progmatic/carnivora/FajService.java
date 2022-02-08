package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FajService {


    @Autowired
    private FajRepository fajRepository;

    public void saveAll(List<Faj> fajok){
        fajRepository.saveAll(fajok);
    }

    public FajTestDto FajToFajTestDto(String nev){
        Faj faj = fajRepository.getByNev(nev);
        return FajTestDto.builder()
                .nev(faj.getNev())
                .szuloNev(faj.getKlad().getNev())
                .leiras(faj.getLeiras())
                .turesHatar(faj.getTuresHatar())
                .veszelyeztetettBesorolas(faj.getStatusz())
                .build();
    }
}
