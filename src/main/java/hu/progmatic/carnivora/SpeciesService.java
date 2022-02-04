package hu.progmatic.carnivora;

import hu.progmatic.databaseinit.GetWholePathOfResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class SpeciesService implements InitializingBean {

    @Autowired
    private SpeciesRepository speciesRepository;

    public SpeciesDto buildSpeciesDto(Species species) {
        return SpeciesDto.builder()
                .id(species.getId())
                .ancestorId(species.getAncestorId())
                .ancestorName(species.getAncestorName())
                .description(species.getDescription())
                .name(species.getName())
                .nameLatin(species.getNameLatin())
                .endangeredClassification(species.getConservationStatus())
                .environmentalEndurance(species.getEnvironmentalEndurance())
                .photURL(species.getPhotURL())
                .wikiURL(species.getWikiURL())
                .build();
    }

    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    public Species create(Species faj) {
        faj.setId(null);
        return speciesRepository.save(faj);
    }

    public Species save(Species faj) {
        return speciesRepository.saveAndFlush(faj);
    }

    public void deleteById(Integer id) {
        speciesRepository.deleteById(id);
    }

    public Species getById(Integer id) {
        return speciesRepository.getById(id);
    }

    public void deleteByIdIfExists(Integer id) {
        if (speciesRepository.existsById(id)) {
            speciesRepository.deleteById(id);
        }
    }

    public Species findById(Integer id) {
        return speciesRepository.findById(id)
                .orElseThrow();
    }

    public List<Species> databasefactory(String filePath) {
        File file = new File(filePath);
        List<Species> ujFaj = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String faj = scanner.nextLine();
                ujFaj.add(getFajRecord(faj));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Nincs meg a file");
        }
        return ujFaj;
    }

    private static Species getFajRecord(String faj) {
        String[] fajinfo = faj.split(",");
        Integer id = null;
        Integer szuloId = Integer.parseInt(fajinfo[0]);
        String szuloNev = fajinfo[1];
        String nev = fajinfo[2];
        String latinNev = fajinfo[3];
        String leiras = fajinfo[4];
        ConservationStatus kategoriak = kategoriaSwitch(fajinfo[5]);
        Endurance tureshatar = turesSwitch(fajinfo[6]);
        String fotoUrl = fajinfo[7];
        String wikiUrl = fajinfo[8];
        Species ujfaj = new Species(id, szuloId, szuloNev, leiras, nev, latinNev, kategoriak, tureshatar, fotoUrl, wikiUrl);
        return ujfaj;
    }

    private static ConservationStatus kategoriaSwitch(String line) {
        String value = String.valueOf(line).toUpperCase();
        return switch (value) {
            case "KIHALT" -> ConservationStatus.KIHALT;
            case "VADON KIHALT" -> ConservationStatus.VADON_KIHALT;
            case "FENYEGETETT" -> ConservationStatus.FENYEGETETT;
            case "SÚLYOSAN VESZÉLYEZTETETT" -> ConservationStatus.SULYOSAN_VESZELYEZTETETT;
            case "VESZÉLYEZTETETT" -> ConservationStatus.VESZELYEZTETETT;
            case "SEBEZHETO" -> ConservationStatus.SEBEZHETO;
            case "MÉRSÉKELTEN FENYEGETETT" -> ConservationStatus.MERSEKELTEN_FENYEGETETT;
            case "NEM FENYEGETETT" -> ConservationStatus.NEM_FENYEGETETT;
            default -> ConservationStatus.HAZIASITOTT;
        };
    }

    private static Endurance turesSwitch(String line) {
        String tures = String.valueOf(line).toUpperCase();
        return switch (tures) {
            case "SPECIALISTA" -> Endurance.SPECIALISTA;
            case "GENERALISTA" -> Endurance.GENERALISTA;
            default -> Endurance.GENERALISTA;
        };
    }

    @Override
    public void afterPropertiesSet() throws URISyntaxException {
        if (speciesRepository.findAll().isEmpty()) {
            String file = GetWholePathOfResource.getWholePath("faj.csv");
            List<Species> ujFaj = databasefactory(file);
            speciesRepository.saveAll(ujFaj);
        }
    }
}