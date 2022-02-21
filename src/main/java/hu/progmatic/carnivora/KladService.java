package hu.progmatic.carnivora;

import hu.progmatic.databaseinit.InitSpeciesFromFileFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
        updateAllPictures();
    }

    private void updateAllPictures() {
        List<Faj> species = fajService.findAll();
        species.forEach(faj -> faj.setKepId(fajService.getPicureId(faj.getLatinNev())));
        fajService.saveAll(species);
    }

    public KladDto getFirstCommonKladAncestorOfFaj(Faj faj1, Faj faj2) {
        for (Klad faj1klad : getBloodLineOfFaj(faj1)) {
            for (Klad faj2klad : getBloodLineOfFaj(faj2)) {
                if (faj1klad.getId().equals(faj2klad.getId())) {
                    return buildKladDtoFromKlad(faj1klad);
                }
            }
        }
        return null;
    }

    public List<Klad> getBloodLineOfFaj(Faj faj) {
        List<Klad> output = new ArrayList<>();
        Klad ancestor = faj.getKlad();

        output.add(ancestor); // enélkül az első Klád szülőt kihagyná a kimeneti listából

        while (ancestor.getSzulo() != null) {
            output.add(ancestor.getSzulo());
            ancestor = ancestor.getSzulo();
        }
        return output;
    }

    public Set<NodeForBloodLineDto> getBloodLineDtoForKozosOs(Faj faj1, Faj faj2) {
        Set<NodeForBloodLineDto> output = new HashSet<>();
        Klad faj1Ancestor = faj1.getKlad();
        Klad faj2Ancestor = faj2.getKlad();

        output.add(NodeForBloodLineDto.builder()
                .key(faj1.getId())
                .name(faj1.getNev())
                .latinNev(faj1.getLatinNev())
                .parent(faj1Ancestor.getId())
                .build());

        output.add(NodeForBloodLineDto.builder()
                .key(faj2.getId())
                .name(faj2.getNev())
                .latinNev(faj2.getLatinNev())
                .parent(faj2Ancestor.getId())
                .build());

        while (faj1Ancestor.getSzulo() != null) {
            output.add(NodeForBloodLineDto.builder()
                    .key(faj1Ancestor.getId())
                    .name(faj1Ancestor.getNev())
                    .latinNev(faj1Ancestor.getLatinNev())
                    .parent(faj1Ancestor.getSzulo().getId())
                    .build());
            faj1Ancestor = faj1Ancestor.getSzulo();
        }

        while (faj2Ancestor.getSzulo() != null) {
            output.add(NodeForBloodLineDto.builder()
                    .key(faj2Ancestor.getId())
                    .name(faj2Ancestor.getNev())
                    .latinNev(faj2Ancestor.getLatinNev())
                    .parent(faj2Ancestor.getSzulo().getId())
                    .build());
            faj2Ancestor = faj2Ancestor.getSzulo();
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
                .kepId(entity.getKepId())
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
                .kepId(klad.getKepId())
                .build();
    }

    public KladDto buildKladDtoFromKlad(Klad klad) {
        return KladDto.builder()
                .id(klad.getId())
                .nev(klad.getNev())
                .latinNev(klad.getLatinNev())
                .leiras(klad.getLeiras())
                .kepId(klad.getKepId())
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
