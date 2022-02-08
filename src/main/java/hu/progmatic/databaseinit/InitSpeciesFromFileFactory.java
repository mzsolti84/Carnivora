package hu.progmatic.databaseinit;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import hu.progmatic.carnivora.Faj;
import hu.progmatic.carnivora.Tureshatar;
import hu.progmatic.carnivora.TermeszetvedelmiStatusz;
import hu.progmatic.carnivora.Klad;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InitSpeciesFromFileFactory {

    private final List<Faj> fajok;

    private final List<Klad> klads;

    public InitSpeciesFromFileFactory(String kladFileName, String speciesFileName) {
        this.klads = new InitKladFromFileFactory(kladFileName).getKlads();
        String fileWholeName;
        try {
            fileWholeName = GetWholePathOfResource.getWholePath(speciesFileName);
        } catch (URISyntaxException e) {
            throw new CsvURISyntaxException(e.getMessage());
        }
        this.fajok = new ArrayList<>();
        try (CSVReader csv = new CSVReader(new InputStreamReader(new FileInputStream(fileWholeName), StandardCharsets.UTF_8))) {
            csv.readNext();
            String[] line;
            while ((line = csv.readNext()) != null) {
                fileRowToEntity(line);
            }
        } catch (IOException e) {
            throw new KladFileException("File not exist!");
        } catch (CsvValidationException e) {
            throw new KladFileException("File is not standard!");
        }
    }

    private void fileRowToEntity(String[] speciesProperties) {
        String parentName = speciesProperties[0];
        String name = speciesProperties[1];
        String nameLatin = speciesProperties[2].equals("null") ? null : speciesProperties[2];
        String description = speciesProperties[3].equals("null") ? null : speciesProperties[3];
        String veszelyeztetett = speciesProperties[4];
        String tureshatar = speciesProperties[5];
        String fotoURL = speciesProperties[6].equals("null") ? null : speciesProperties[6];
        String wikiURL = speciesProperties[7].equals("null") ? null : speciesProperties[7];

        Klad parent = klads.stream().filter(klad -> klad.getNev().equals(parentName)).findAny().orElseThrow();
        Faj givenFaj = Faj.builder()
                .klad(parent)
                .nev(name)
                .latinNev(nameLatin)
                .leiras(description)
                .statusz(kategoriaSwitch(veszelyeztetett))
                .turesHatar(turesSwitch(tureshatar))
                .fotoURL(fotoURL)
                .wikiURL(wikiURL)
                .build();
        fajok.add(givenFaj);
        parent.getFajLista().add(givenFaj);
    }

    public static TermeszetvedelmiStatusz kategoriaSwitch(String line) {
        String value = String.valueOf(line).toUpperCase();
        return switch (value) {
            case "KIHALT" -> TermeszetvedelmiStatusz.KIHALT;
            case "VADON KIHALT" -> TermeszetvedelmiStatusz.VADON_KIHALT;
            case "FENYEGETETT" -> TermeszetvedelmiStatusz.FENYEGETETT;
            case "SÚLYOSAN VESZÉLYEZTETETT" -> TermeszetvedelmiStatusz.SULYOSAN_VESZELYEZTETETT;
            case "VESZÉLYEZTETETT" -> TermeszetvedelmiStatusz.VESZELYEZTETETT;
            case "SEBEZHETO" -> TermeszetvedelmiStatusz.SEBEZHETO;
            case "MÉRSÉKELTEN FENYEGETETT" -> TermeszetvedelmiStatusz.MERSEKELTEN_FENYEGETETT;
            case "NEM FENYEGETETT" -> TermeszetvedelmiStatusz.NEM_FENYEGETETT;
            default -> TermeszetvedelmiStatusz.HAZIASITOTT;
        };
    }

    public static Tureshatar turesSwitch(String line) {
        String tures = String.valueOf(line).toUpperCase();
        return switch (tures) {
            case "SPECIALISTA" -> Tureshatar.SPECIALISTA;
            case "GENERALISTA" -> Tureshatar.GENERALISTA;
            default -> Tureshatar.GENERALISTA;
        };
    }


    public List<Faj> getSpecies() {
        return fajok;
    }

    public List<Klad> getKlads() {
        return klads;
    }
}
