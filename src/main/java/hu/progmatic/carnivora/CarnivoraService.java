package hu.progmatic.carnivora;

import hu.progmatic.databaseinit.GetWholePathOfResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
public class CarnivoraService implements InitializingBean {

    @Autowired
    private FajRepository fajRepository;

    public List<Faj> findAll() {
        return fajRepository.findAll();
    }

    public Faj create(Faj faj) {
        faj.setId(null);
        return fajRepository.save(faj);
    }

    public Faj save(Faj faj) {
        return fajRepository.saveAndFlush(faj);
    }

    public void deleteById(Integer id) {
        fajRepository.deleteById(id);
    }

    public Faj getById(Integer id) {
        return fajRepository.getById(id);
    }

    public void deleteByIdIfExists(Integer id) {
        if (fajRepository.existsById(id)) {
            fajRepository.deleteById(id);
        }
    }

    public Faj findById(Integer id) {
        return fajRepository.findById(id)
                .orElseThrow();
    }

    public List<Faj> databaseFactory(String filePath) {
        File file = new File(filePath);
        List<Faj> ujFaj = new LinkedList<>();
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

    private static Faj getFajRecord(String faj) {
        String[] fajinfo = faj.split(",");
        Integer id = null;
        //Integer szuloId = Integer.parseInt(fajinfo[0]);
        String szuloNev = fajinfo[0];
        String nev = fajinfo[1];
        String latinNev = fajinfo[2];
        String leiras = fajinfo[3];
        VeszelyeztetettKategoriak kategoriak = kategoriaSwitch(fajinfo[4]);
        Tureshatar tureshatar = turesSwitch(fajinfo[5]);
        String fotoUrl = fajinfo[6];
        String wikiUrl = fajinfo[7];
        Faj ujfaj = new Faj(id, null, szuloNev, leiras, nev, latinNev, kategoriak, tureshatar, fotoUrl, wikiUrl);
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

    private static Tureshatar turesSwitch(String line) {
        String tures = String.valueOf(line).toUpperCase();
        return switch (tures) {
            case "SPECIALISTA" -> Tureshatar.SPECIALISTA;
            case "GENERALISTA" -> Tureshatar.GENERALISTA;
            default -> Tureshatar.GENERALISTA;
        };
    }

    @Override
    public void afterPropertiesSet() throws URISyntaxException {
        if (fajRepository.findAll().isEmpty()) {
            String file = GetWholePathOfResource.getWholePath("faj.csv");
            List<Faj> ujFaj = databaseFactory(file);
            fajRepository.saveAll(ujFaj);
        }
    }

}