package hu.progmatic.service;

import hu.progmatic.carnivora.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FajServiceTest {

    @Autowired
    private FajService fajService;
    @Autowired
    private KladRepository kladRepository;

    @Test
    void speciesRecordTest() {
        FajDto speciesTest = fajService.buildFajDtoByFajNev("Fossza");
        assertEquals("Madagaszkár legnagyobb ragadozó állata: a nőstény testhossza 65–70 centiméter, a hímé 75–80 centiméter, farokhossza 70–90 centiméter, a nőstény testtömege 5–7 kilogramm, a hímé 6–10 kilogramm. Különösen szembeötlővé válik ez a macskaszerűség, ha a fosszát a vele egyező színezetű, megnyúlt alakú, alacsony tairával (Eira barbara) vetjük össze. A hasonlóság miatt a rendszertani helye is hosszú ideig vitás volt.",
                speciesTest.getLeiras());
        assertEquals(TermeszetvedelmiStatusz.SEBEZHETO, speciesTest.getStatusz());
        assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());


        speciesTest = fajService.buildFajDtoByFajNev("Hosszúfarkú Vidra");
        assertEquals("A hosszúfarkú vidra átlag fej-testhossza 60-100 centiméter, farokhossza pedig 30-50 centiméter. Testtömege 5-15 kilogramm között lehet. A hímek nagyobbak a nőstényeknél. Ez a vidrafaj számos vizes helyen található meg. Általában a nagy folyók közelségét keresi. Egyaránt megél a lombhullató- és örökzöld erdőkben, a nagy mocsaras területeken, valamint a szavannákon is. Állományai nagyobbak és egészségesebbek a magasabban levő gyorsabb folyású folyókban és patakokban, mint az alföldek állóvizeiben. Fő táplálékai a halak, a kagylók és a puhatestűek, de rovarokat, madarakat, kisebb emlősöket és hüllőket is elfogyaszt. Magányosan él és vadászik.",
                speciesTest.getLeiras());
        assertEquals(TermeszetvedelmiStatusz.SULYOSAN_VESZELYEZTETETT, speciesTest.getStatusz());
        assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());
    }

    @Test
    void speciesParentCladTest() {
        FajDto speciesTest = fajService.buildFajDtoByFajNev("Fossza");
        assertEquals("madagaszkári cibetmacskafélék családja", speciesTest.getSzuloNev());

        speciesTest = fajService.buildFajDtoByFajNev("Kinkaju");
        assertEquals("mosómedvefélék családja", speciesTest.getSzuloNev());
    }

    @Test
    @DisplayName("Elem létrehozása")
    void create() {
        FajDto data = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                .build();
        Faj saved = fajService.save(data);
        assertNotNull(saved.getId());
    }

    @Test
    @DisplayName("A leírás mezőbe bele tudunk írni több mint 255 karaktert")
    void textMezoHosszabbMint255() {
        String hosszuLeiras = "1234567890123456789012345678901234567890123456789012" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "345678901234567890123456789012345678901234567890123456789012345678" +
                "901234567890123456789012345678901234567890123456789012345678901234" +
                "567890123456789012345678901234567890123456789012345678901234567890";
        FajDto data = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz sp. Magyarikusz")
                .leiras(hosszuLeiras)
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                .build();
        Faj saved = fajService.save(data);
        assertEquals(hosszuLeiras, saved.getLeiras());
    }

    @Test
    @DisplayName("A latinNev mező egyedi")
    void createUnique() {
        String latinNev = "Tyukusz Tyukusz";
        FajDto data = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev(latinNev)
                .leiras("Tyúúúúk")
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                .build();

        fajService.save(data);

        FajDto data2 = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev(latinNev)
                .leiras("Tyúúúúk")
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                .build();

        Exception exception = null;

        try {
            fajService.save(data2);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertThat(exception.getCause().getCause().getMessage())
                .contains("Unique index or primary key violation");
    }

    @Nested
    @DisplayName("Teszt elemekkel")
    class TesztElemmelTest {

        private Faj fajData;

        @BeforeEach
        void setUp() {
            FajDto fajDataInit = FajDto.builder()
                    .id(null)
                    .nev("Szürke farkas")
                    .latinNev("Canis lupus sp.")
                    .turesHatar(Tureshatar.GENERALISTA)
                    .leiras("Ordas Farkas")
                    .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                    .build();

            fajData = fajService.save(fajDataInit);
        }

        @AfterEach
        void tearDown() {
            fajService.deleteFajByIdIfExists(fajData.getId());
        }

        @Test
        @DisplayName("Elem lekérdezése id alapján")
        void getId() {
            Faj read = fajService.getById(fajData.getId());
            assertNotNull(read.getId());
            assertEquals("Szürke farkas", read.getNev());
            assertEquals("Canis lupus sp.", read.getLatinNev());
            assertEquals(Tureshatar.GENERALISTA, read.getTuresHatar());
        }

        @Test
        @DisplayName("Elem törlése")
        void delete() {
            Faj read = fajService.getById(fajData.getId());
            assertNotNull(read.getId());
            fajService.deleteFajByIdIfExists(fajData.getId());
            Exception exception = null;
            try {
                Faj readAfterDelete = fajService.getById(fajData.getId());
                assertNotNull(readAfterDelete.getLatinNev());
            } catch (Exception e) {
                exception = e;
            }
            assertNotNull(exception);
        }

        @Test
        @DisplayName("Elem frissítése")
        void update() {
            Faj data = fajService.getById(fajData.getId());
            data.setNev("Új név");
            Faj read = fajService.getById(data.getId());
            assertEquals("Új név", read.getNev());
        }
    }

    @Test
    @DisplayName("Kép neve jól jelenik meg")
    void pictureName() {
        String st = "Canis lupus sp";
        assertEquals("Canis_lupus_sp", fajService.replaceSpaceInMegnevezes(st, false));
    }

    @Test
    @DisplayName("Az alkalmazás indításakor léteznek fajok")
    void getAllFajDto() {
        List<FajDto> fajLista = fajService.getAllFajDto();
        assertNotNull(fajLista);
        assertNotNull(fajLista.get(0).getLatinNev());

        assertThat(fajLista)
                .extracting(FajDto::getNev)
                .containsAnyElementsOf(List.of("Házimacska", "Szürkefarkas"));
    }

    @Test
    @DisplayName("Create függvény működik")
    void create_function() {
        FajDto data = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(kladRepository.findByNevEquals("macskaalkatúak alrendje").getId())
                .build();
        fajService.create(data);

        Faj faj = fajService.getByNev("Tyúk");
        assertNotNull(faj);
        assertEquals("Tyukusz Udvarikusz", faj.getLatinNev());
        assertEquals("Háziasított", faj.getStatusz().getDisplayName());
    }

}