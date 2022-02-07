package hu.progmatic.databaseinit;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import hu.progmatic.carnivora.KladEntity;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class InitKladFromFileFactory {

    private final List<KladEntity> klads;

    public InitKladFromFileFactory(String fileName) {
        String fileWholeName;
        try {
            fileWholeName = GetWholePathOfResource.getWholePath(fileName);
        } catch (URISyntaxException e) {
            throw new CsvURISyntaxException(e.getMessage());
        }
        this.klads = new ArrayList<>();
        try (CSVReader csv = new CSVReader(new InputStreamReader(new FileInputStream(fileWholeName), StandardCharsets.UTF_8))) {
            csv.readNext();
            String[] line;
            while ((line = csv.readNext()) != null) {
                fileRowToEntity(line);
            }
        } catch (IOException e) {
            throw new SpeciesFileException("File not exist!");
        } catch (CsvValidationException e) {
            throw new SpeciesFileException("File is not standard!");
        }


    }


    private void fileRowToEntity(String[] kladProperties) {
        //String[] kladProperties = fileRow.split(",");
        String name = kladProperties[0].equals("null") ? null : kladProperties[0];
        String latinName = kladProperties[1];
        String parent = kladProperties[2].equals("null") ? null : kladProperties[2];
        String description = kladProperties[3].equals("null") ? null : kladProperties[3];
        if (parent == null) {
            klads.add(KladEntity.builder()
                    .name(name)
                    .latinName(latinName)
                    .description(description)
                    .parent(null)
                    .build());

        } else {
            KladEntity parentObject = klads.stream().filter(klad -> klad.getName().equals(parent)).findFirst().orElseThrow();
            KladEntity givenKlad = KladEntity.builder()
                    .name(name)
                    .latinName(latinName)
                    .description(description)
                    .parent(parentObject)
                    .build();
            parentObject.getChildren().add(givenKlad);
            klads.add(givenKlad);
        }

    }

    public List<KladEntity> getKlads() {
        return klads;
    }
}
