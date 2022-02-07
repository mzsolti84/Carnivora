package hu.progmatic.databaseinit;

import hu.progmatic.carnivora.Klad;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InitKladFromFileTest {

    @Test
    void fileException() {
        String exception = null;
        try {
            new InitKladFromFileFactory("error.csv");
        } catch (CsvURISyntaxException e) {
            exception = e.getMessage();
        }
        assertNotNull(exception);
    }

    @Test
    void iniFileAlrendMacskaTest() {
        InitKladFromFileFactory initKlad = new InitKladFromFileFactory("klad.csv");
        List<Klad> klads = initKlad.getKlads();

        Klad givenKlad = klads.stream()
                .filter(klad -> klad.getNev().equals("macskaalkatúak alrendje"))
                .findFirst().orElseThrow();

        assertThat(givenKlad.getChildren())
                .extracting(Klad::getNev)
                .containsExactlyInAnyOrder("madagaszkári cibetmacskafélék családja",
                        "macskafélék családja",
                        "mongúzfélék családja",
                        "hiénafélék családja",
                        "cibetmacskafélék családja",
                        "pálmacibetfélék családja");


    }

    @Test
    void iniFileAlrendKutyaTest() {
        InitKladFromFileFactory initKlad = new InitKladFromFileFactory("klad.csv");
        List<Klad> klads = initKlad.getKlads();

        Klad givenKlad = klads.stream()
                .filter(klad -> klad.getNev().equals("kutyaalkatúak alrendje"))
                .findFirst().orElseThrow();

        assertThat(givenKlad.getChildren())
                .extracting(Klad::getNev)
                .containsExactlyInAnyOrder("macskamedvefélék családja",
                        "kutyafélék családja",
                        "bűzösborzfélék családja",
                        "menyétfélék családja",
                        "mosómedvefélék családja",
                        "medvefélék családja",
                        "úszólábúak öregcsaládja");
    }
}
