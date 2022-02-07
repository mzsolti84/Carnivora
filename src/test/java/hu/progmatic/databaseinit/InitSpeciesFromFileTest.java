package hu.progmatic.databaseinit;

import hu.progmatic.carnivora.Tureshatar;
import hu.progmatic.carnivora.VeszelyeztetettKategoriak;
import hu.progmatic.carnivora.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitSpeciesFromFileTest {

    @Test
    void fileException() {
        String exception = null;
        try {
            new InitSpeciesFromFileFactory("klad.csv", "error.csv");
        } catch (CsvURISyntaxException e) {
            exception = e.getMessage();
        }
        assertNotNull(exception);
    }

    @Nested
    class speciesTest {

        List<Species> speciesList;

        @BeforeEach
        void setUp() {
            InitSpeciesFromFileFactory init = new InitSpeciesFromFileFactory("klad.csv", "faj.csv");
            this.speciesList = init.getSpecies();
        }

        @Test
        void speciesRecordTest() {
            Species speciesTest = speciesList.stream().filter(speces -> speces.getName().equals("Fossza")).findAny().orElseThrow();
            assertEquals("Madagaszkár legnagyobb ragadozó állata: a nőstény testhossza 65–70 centiméter, a hímé 75–80 centiméter, farokhossza 70–90 centiméter, a nőstény testtömege 5–7 kilogramm, a hímé 6–10 kilogramm. Különösen szembeötlővé válik ez a macskaszerűség, ha a fosszát a vele egyező színezetű, megnyúlt alakú, alacsony tairával (Eira barbara) vetjük össze. A hasonlóság miatt a rendszertani helye is hosszú ideig vitás volt.",
                    speciesTest.getDescription());
            assertEquals(VeszelyeztetettKategoriak.SEBEZHETO, speciesTest.getVeszelyeztetettBesorolas());
            assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());


            speciesTest = speciesList.stream().filter(speces -> speces.getName().equals("Hosszúfarkú Vidra")).findAny().orElseThrow();
            assertEquals("A hosszúfarkú vidra átlag fej-testhossza 60-100 centiméter, farokhossza pedig 30-50 centiméter. Testtömege 5-15 kilogramm között lehet. A hímek nagyobbak a nőstényeknél. Ez a vidrafaj számos vizes helyen található meg. Általában a nagy folyók közelségét keresi. Egyaránt megél a lombhullató- és örökzöld erdőkben, a nagy mocsaras területeken, valamint a szavannákon is. Állományai nagyobbak és egészségesebbek a magasabban levő gyorsabb folyású folyókban és patakokban, mint az alföldek állóvizeiben. Fő táplálékai a halak, a kagylók és a puhatestűek, de rovarokat, madarakat, kisebb emlősöket és hüllőket is elfogyaszt. Magányosan él és vadászik.",
                    speciesTest.getDescription());
            assertEquals(VeszelyeztetettKategoriak.SULYOSAN_VESZELYEZTETETT, speciesTest.getVeszelyeztetettBesorolas());
            assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());
        }

        @Test
        void speciesParentCladTest() {
            Species speciesTest = speciesList.stream().filter(speces -> speces.getName().equals("Fossza")).findAny().orElseThrow();
            assertEquals("madagaszkári cibetmacskafélék családja", speciesTest.getClad().getName());

            speciesTest = speciesList.stream().filter(speces -> speces.getName().equals("Kinkaju")).findAny().orElseThrow();
            assertEquals("mosómedvefélék családja", speciesTest.getClad().getName());
        }
    }
}