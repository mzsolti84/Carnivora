package hu.progmatic.databaseinit;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import hu.progmatic.carnivora.Tureshatar;
import hu.progmatic.carnivora.VeszelyeztetettKategoriak;
import hu.progmatic.carnivora.Klad;
import hu.progmatic.carnivora.Species;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InitSpeciesFromFileFactory {

    private final List<Species> species;

    private final List<Klad> klads;

    public InitSpeciesFromFileFactory(String kladFileName, String speciesFileName) {
        this.klads = new InitKladFromFileFactory(kladFileName).getKlads();
        String fileWholeName;
        try {
            fileWholeName = GetWholePathOfResource.getWholePath(speciesFileName);
        } catch (URISyntaxException e) {
            throw new CsvURISyntaxException(e.getMessage());
        }
        this.species = new ArrayList<>();
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

        Klad parent = klads.stream().filter(klad -> klad.getName().equals(parentName)).findAny().orElseThrow();
        Species givenSpecies = Species.builder()
                .clad(parent)
                .name(name)
                .nameLatin(nameLatin)
                .description(description)
                .veszelyeztetettBesorolas(kategoriaSwitch(veszelyeztetett))
                .turesHatar(turesSwitch(tureshatar))
                .fotoURL(fotoURL)
                .wikiURL(wikiURL)
                .build();
        species.add(givenSpecies);
        parent.getSpeciesList().add(givenSpecies);
    }

    public static VeszelyeztetettKategoriak kategoriaSwitch(String line) {
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

    public static Tureshatar turesSwitch(String line) {
        String tures = String.valueOf(line).toUpperCase();
        return switch (tures) {
            case "SPECIALISTA" -> Tureshatar.SPECIALISTA;
            case "GENERALISTA" -> Tureshatar.GENERALISTA;
            default -> Tureshatar.GENERALISTA;
        };
    }


    public List<Species> getSpecies() {
        return species;
    }

    public List<Klad> getKlads() {
        return klads;
    }
}
