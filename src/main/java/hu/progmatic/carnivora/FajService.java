package hu.progmatic.carnivora;


import hu.progmatic.carnivora.kepkezeles.KepRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static hu.progmatic.CarnivoraApplication.projectHostName;


@Service
@Transactional
public class FajService {

    @Autowired
    private FajRepository fajRepository;

    @Autowired
    private KladRepository kladRepository;

    @Autowired
    KepRepository kepRepository;

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
        return DtoBuilderFromFaj(faj);
    }

    private FajDto DtoBuilderFromFaj(Faj faj) {
        return FajDto.builder()
                .id(faj.getId())
                .leiras(faj.getLeiras())
                .nev(faj.getNev())
                .latinNev(faj.getLatinNev())
                .statusz(faj.getStatusz())
                .turesHatar(faj.getTuresHatar())
                .fotoURL(getURLFromKepMegnevezes(faj.getLatinNev(), projectHostName))
                .kepId(faj.getKepId())
                .wikiURL(faj.getWikiURL())
                .szuloNev(faj.getKlad().getNev())
                .szuloId(faj.getKlad().getId())
                .build();
    }

    public FajDto buildFajDtoByFajId(Integer id) {
        Faj faj = fajRepository.getById(id);
        return DtoBuilderFromFaj(faj);
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
                .kepId(fajDto.getKepId())
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

    public String replaceSpaceInMegnevezes(String input, Boolean reverse) {
        if (!reverse) {
            return input.replaceAll(" ", "_");
        } else {
            return input.replaceAll("_", " ");
        }
    }

    public String getURLFromKepMegnevezes(String latinMegnevezes, String host) {
        Integer id = getPicureId(latinMegnevezes);
            return host + "/kepkezeles/" + id;
    }

    public Faj getByNev(String nev) {
        return fajRepository.getByNev(nev);
    }


    public Integer getPicureId(String latinMegnevezes) {
        String kepNev = replaceSpaceInMegnevezes(latinMegnevezes, false);
        if (kepRepository.getByMegnevezesIgnoreCase(kepNev) != null) {
            return kepRepository.getByMegnevezesIgnoreCase(kepNev).getId();
        }
        return 0;
    }

    public List<Faj> findAll() {
        return fajRepository.findAll();
    }
}
