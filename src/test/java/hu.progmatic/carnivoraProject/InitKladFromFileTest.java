package hu.progmatic.carnivoraProject;

import hu.progmatic.iniklad.InitKladFromFileFactory;
import hu.progmatic.iniklad.KladURISyntaxException;
import hu.progmatic.klad.KladEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InitKladFromFileTest {

    @Test
    void fileException() {
        String exception = null;
        try{
            new InitKladFromFileFactory("error.csv");
        }catch (KladURISyntaxException e){
            exception = e.getMessage();
        }
        assertNotNull(exception);
    }

    @Test
    void iniFileAlrendMacskaTest() {
        InitKladFromFileFactory initKlad = new InitKladFromFileFactory("klad.csv");
        List<KladEntity> klads = initKlad.getKlads();

        KladEntity givenKlad = klads.stream()
                .filter(klad->klad.getName().equals("macskaalkatúak alrendje"))
                .findFirst().orElseThrow();

        assertThat(givenKlad.getChildren())
                .extracting(KladEntity::getName)
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
        List<KladEntity> klads = initKlad.getKlads();

        KladEntity givenKlad = klads.stream()
                .filter(klad->klad.getName().equals("kutyaalkatúak alrendje"))
                .findFirst().orElseThrow();

        assertThat(givenKlad.getChildren())
                .extracting(KladEntity::getName)
                .containsExactlyInAnyOrder("macskamedvefélék családja",
                        "kutyafélék családja",
                        "bűzösborzfélék családja",
                        "menyétfélék családja",
                        "mosómedvefélék családja",
                        "medvefélék családja",
                        "úszólábúak öregcsaládja");
    }
}
