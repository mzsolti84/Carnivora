package hu.progmatic.carnivora;

import hu.progmatic.databaseinit.InitSpeciesFromFileFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class KladService implements InitializingBean {

    @Autowired
    private KladRepository kladRepository;

    @Autowired
    private FajService fajService;

    @Override
    public void afterPropertiesSet() {
        if (kladRepository.count() == 0) {
            InitSpeciesFromFileFactory init = new InitSpeciesFromFileFactory("klad.csv", "faj.csv");
            kladRepository.saveAll(init.getKlads());
            fajService.saveAll(init.getSpecies());
        }
    }

    public Klad getFirstCommonKladAncestorOfFaj(Faj faj1, Faj faj2) {
        List<Klad> faj1Bloodline = getBloodLineOfFaj(faj1);
        List<Klad> faj2Bloodline = getBloodLineOfFaj(faj2);

        for (Klad klad1 : faj1Bloodline) {
            for (Klad klad2 : faj2Bloodline) {
                if (klad1.getId().equals(klad2.getId())) {
                    return klad1;
                }
            }
        }
        return null;
    }

    public List<Klad> getBloodLineOfFaj(Faj faj) {
        List<Klad> output = new ArrayList<>();
        Klad ancestor = faj.getKlad();

        output.add(ancestor);

        while (ancestor.getSzulo() != null) {
            output.add(ancestor.getSzulo());
            ancestor = ancestor.getSzulo();
        }
        return output;
    }

    public KladWithChildrenDto getKladDtoByName(String name) {
        Klad entity = kladRepository.findByNevEquals(name);

        return buildKladWithChildrenDto(entity);
    }

    private KladWithChildrenDto buildKladWithChildrenDto(Klad entity) {
        return KladWithChildrenDto.builder()
                .nev(entity.getNev())
                .latinNev(entity.getLatinNev())
                .leiras(entity.getLeiras())
                .szulo(entity.getSzulo().getNev())
                .leszarmazott(
                        entity.getLeszarmazottak().stream()
                                .map(this::buildKladWithChildrenDto)
                                .toList()
                )
                .build();
    }

    public List<KladWithChildrenDto> findAllWithNoChild() {
        List<Klad> noChildrens = kladRepository.findAllWithNoChild();
        return noChildrens.stream().map(this::buildKladWithChildrenDto).toList();
    }

    public List<SzuloKladDto> findAllParentKlad() {
        return kladRepository.findAllWithNoChild().stream()
                .map(this::kladToParentKladDto)
                .toList();
    }

    private SzuloKladDto kladToParentKladDto(Klad klad) {
        return SzuloKladDto.builder()
                .id(klad.getId())
                .nev(klad.getNev())
                .build();
    }

    public KladDto buildKladDtoFromKlad(Klad klad) {
        return KladDto.builder()
                .id(klad.getId())
                .nev(klad.getNev())
                .latinNev(klad.getLatinNev())
                .leiras(klad.getLeiras())
                .szuloId(klad.getSzulo() == null ? 0 : klad.getSzulo().getId())
                .szuloNev(klad.getSzulo() == null ? "Eukarióták" : klad.getSzulo().getNev())
                .build();
    }

    public KladDto getKladDtoById(Integer id) {
        Klad klad = kladRepository.getById(id);
        return buildKladDtoFromKlad(klad);
    }

    public List<FajDto> getFajDtoListFromKladId(Integer id) {
        List<FajDto> fajok = fajService.getAllFajDto();
        List<FajDto> kladFaj = new ArrayList<>();
        for (FajDto faj : fajok) {
            if (Objects.equals(faj.getSzuloId(), id)) {
                kladFaj.add(faj);
            }
        }
        return kladFaj;
    }
}
