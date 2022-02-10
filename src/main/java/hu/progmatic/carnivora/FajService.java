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

    @Autowired
    private KladRepository kladRepository;

    public void saveAll(List<Faj> fajok) {
        fajRepository.saveAll(fajok);
    }

    public List<FajDto> getAllFajDto() {
        return fajRepository.findAll().stream()
                .map(faj -> buildFajDtoByFajId(faj.getId()))
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

    public FajDto buildFajDtoByFajId(Integer id) {
        Faj faj = fajRepository.getById(id);
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

    private Klad getKladFromFajDto(FajDto fajDto) {
        return kladRepository.getById(fajDto.getSzuloId());
    }

    public Faj buildFajFromFajDto(FajDto fajDto) {
        return Faj.builder()
                .id(fajDto.getId())
                .nev(fajDto.getNev())
                .leiras(fajDto.getLeiras())
                .latinNev(fajDto.getLatinNev())
                .statusz(fajDto.getStatusz())
                .turesHatar(fajDto.getTuresHatar())
                .fotoURL(fajDto.getFotoURL())
                .wikiURL(fajDto.getWikiURL())
                .klad(getKladFromFajDto(fajDto))
                .build();
    }

    public FajDto getFajDtoByFajId(Integer id) {
        return buildFajDtoByFajId(id);
    }

    public Faj save(FajDto fajDto) {
        return fajRepository.saveAndFlush(buildFajFromFajDto(fajDto));
    }

    public Faj create(FajDto fajDto) {
        Faj faj = buildFajFromFajDto(fajDto);
        faj.setId(null);
        return fajRepository.save(faj);
    }

    public boolean existsById(Integer id) {
        return fajRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        fajRepository.deleteById(id);
    }

    public void deleteFajByIdIfExists(Integer id) {
        if (existsById(id)) {
            deleteById(id);
        }
    }

    public Faj getById(Integer id) {
        return fajRepository.getById(id);
    }
}
