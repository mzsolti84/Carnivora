package hu.progmatic.iniklad;

import hu.progmatic.klad.KladEntity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitKladFromFileFactory {

    private String fileWholeName;
    private List<KladEntity> klads;

    public InitKladFromFileFactory(String fileWholeName) {
        try {
            this.fileWholeName = ResourceKladCsv.getWholePath(fileWholeName);
        } catch (URISyntaxException e) {
            throw new KladURISyntaxException(e.getMessage());
        }
        this.klads = new ArrayList<>();
        File file = new File(this.fileWholeName);
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                fileRowToEntity(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new KladFileException("File not exist");
        }


    }


    private void fileRowToEntity(String fileRow) {
        String[] kladProperties = fileRow.split(",");
        String name = kladProperties[0].equals("null") ? null : kladProperties[1];
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
