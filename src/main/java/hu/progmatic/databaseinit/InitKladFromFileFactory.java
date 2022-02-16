package hu.progmatic.databaseinit;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import hu.progmatic.carnivora.Klad;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class InitKladFromFileFactory {

    private final List<Klad> klads;

    public InitKladFromFileFactory(String fileName) {
        this.klads = new ArrayList<>();
        InputStream inputStream = GetWholePathOfResource.getWholePath(fileName);
        try (CSVReader csv = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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


    private void fileRowToEntity(String[] kladProperties) {
        String name = kladProperties[0].equals("null") ? null : kladProperties[0];
        String latinName = kladProperties[1];
        String parent = kladProperties[2].equals("null") ? null : kladProperties[2];
        String description = kladProperties[3].equals("null") ? null : kladProperties[3];
        if (parent == null) {
            klads.add(Klad.builder()
                    .nev(name)
                    .latinNev(latinName)
                    .leiras(description)
                    .szulo(null)
                    .build());

        } else {
            Klad parentObject = klads.stream().filter(klad -> klad.getNev().equals(parent)).findFirst().orElseThrow();
            Klad givenKlad = Klad.builder()
                    .nev(name)
                    .latinNev(latinName)
                    .leiras(description)
                    .szulo(parentObject)
                    .build();
            parentObject.getLeszarmazottak().add(givenKlad);
            klads.add(givenKlad);
        }

    }

    public List<Klad> getKlads() {
        return klads;
    }
}
