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
public class CarnivoraService implements InitializingBean {

    @Autowired
    private SpeciesRepository speciesRepository;

    @Override
    public void afterPropertiesSet() throws URISyntaxException {
        if (speciesRepository.findAll().isEmpty()) {
            String file = GetWholePathOfResource.getWholePath("faj.csv");
           List<FajRecord> ujFaj = databasefactory(file);
            speciesRepository.saveAll(ujFaj);
        }
    }

    public List<FajRecord> findAll() {
        return speciesRepository.findAll();
    }

    public FajRecord create(FajRecord faj) {
        faj.setId(null);
        return speciesRepository.save(faj);
    }

    public FajRecord save(FajRecord faj) {
        return speciesRepository.saveAndFlush(faj);
    }

    public void deleteById(Integer id) {
        speciesRepository.deleteById(id);
    }

    public FajRecord getById(Integer id) {
        return speciesRepository.getById(id);
    }

    public void deleteByIdIfExists(Integer id) {
        if (speciesRepository.existsById(id)) {
            speciesRepository.deleteById(id);
        }
    }

    public FajRecord empty() {
        return new FajRecord();
    }

    public FajRecord findById(Integer id) {
        return speciesRepository.findById(id)
                .orElseThrow();
    }

    public List<FajRecord> databasefactory(String filePath) {
        File file = new File(filePath);
        List<FajRecord> ujFaj = new LinkedList<>();
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

    private static FajRecord getFajRecord(String faj) {
        String[] fajinfo = faj.split(",");
        Integer id = null;
        Integer szuloId = Integer.parseInt(fajinfo[0]);
        String szuloNev = fajinfo[1];
        String nev = fajinfo[2];
        String latinNev = fajinfo[3];
        String leiras = fajinfo[4];
        VeszelyeztetettKategoriak kategoriak = kategoriaSwitch(fajinfo[5]);
        Tureshatar tureshatar = turesSwitch(fajinfo[6]);
        String fotoUrl = fajinfo[7];
        String wikiUrl = fajinfo[8];
        FajRecord ujfaj = new FajRecord(id, szuloId, szuloNev, leiras, nev, latinNev, kategoriak, tureshatar, fotoUrl, wikiUrl);
        return ujfaj;
    }

    private static VeszelyeztetettKategoriak kategoriaSwitch(String line) {
        String value = String.valueOf(line).toUpperCase();
        return switch (value) {
            case "KIHALT" -> VeszelyeztetettKategoriak.KIHALT;
            case "VADON KIHALT" -> VeszelyeztetettKategoriak.VADON_KIHALT;
            case "FENYEGETETT" -> VeszelyeztetettKategoriak.FENYEGETETT;
            case "SÚLYOSAN VESZÉLYEZTETETT" -> VeszelyeztetettKategoriak.SULYOSAN_VESZELYEZTETETT;
            case "VESZÉLYEZTETETT" -> VeszelyeztetettKategoriak.VESZELYEZTETETT;
            case "SEBEZHETO" -> VeszelyeztetettKategoriak.SEBEZHETO;
            case "MÉRSÉKELTEN FENYEGETETT" -> VeszelyeztetettKategoriak.MERSEKELTEN_FENYEGETETT;
            case "NEM FENYEGETETT" -> VeszelyeztetettKategoriak.NEM_FENYEGETETT;
            default -> VeszelyeztetettKategoriak.HAZIASITOTT;
        };
    }

    private static Tureshatar turesSwitch(String line){
        String tures = String.valueOf(line).toUpperCase();
        return switch (tures){
            case "SPECIALISTA" -> Tureshatar.SPECIALISTA;
            case "GENERALISTA" -> Tureshatar.GENERALISTA;
            default -> Tureshatar.GENERALISTA;
        };
    }
}