package hu.progmatic.carnivora;


import hu.progmatic.carnivora.kepkezeles.KepRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    KepRepository kepRepository;

    @Value("${spring.serverUrl}")
    public String projectHostName;

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

    public void latinNevValidacio(String latinNev){
        Faj faj = fajRepository.getFajByLatinNev(latinNev);
        if (faj != null){
            throw new NemUniqueLatinNevException("Ilyen latin név már szerepel az adatbázisban!");
        }
    }

    public void latinNevValidacio(String latinNev, Integer id){
        Faj faj = fajRepository.getFajByLatinNev(latinNev);
        if (faj != null && !faj.getId().equals(id)){
            throw new NemUniqueLatinNevException("Ilyen latin név már szerepel az adatbázisban!");
        }
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
        if (id != null) {
            return host + "/kepkezeles/" + id;
        } else {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/Carnivora_Diversity.jpg/800px-Carnivora_Diversity.jpg";
        }
    }

    public Faj getByNev(String nev) {
        return fajRepository.getByNev(nev);
    }


    public Integer getPicureId(String latinMegnevezes) {
        String name = replaceSpaceInMegnevezes(latinMegnevezes, false);
        if (kepRepository.getByMegnevezesIgnoreCase(name) != null) {
            return kepRepository.getByMegnevezesIgnoreCase(name).getId();
        }
        return null;
    }

    public List<Faj> findAll() {
        return fajRepository.findAll();
    }
}
