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

    public void saveAll(List<Faj> fajok) {
        fajRepository.saveAll(fajok);
    }

    public List<FajDto> getAllFajDto() {
        return fajRepository.findAll().stream()
                .map(faj -> buildFajDtoByFajNev(faj.getNev()))
                .toList();
    }

    public FajDto buildFajDtoByFajNev(String nev) {
        Faj faj = fajRepository.getByNev(nev);
        return FajDto.builder()
                .id(faj.getId())
                .leiras(faj.getLeiras())
                .nev(faj.getNev())
                .latinNev(faj.getLatinNev())
                .statusz(faj.getStatusz())
                .turesHatar(faj.getTuresHatar())
                .fotoURL(faj.getFotoURL())
                .wikiURL(faj.getWikiURL())
                .szuloNev(faj.getKlad().getNev())
                .szuloId(faj.getKlad().getId())
                .build();
    }
}
