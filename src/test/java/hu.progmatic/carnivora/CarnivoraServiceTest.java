package hu.progmatic.carnivora;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CarnivoraServiceTest {
    @Autowired
    CarnivoraService service;

    @Test
    @DisplayName("Elem létrehozása")
    void create() {
        Faj data = Faj.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        Faj saved = service.save(data);
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
        Faj data = Faj.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz sp. Magyarikusz")
                .leiras(hosszuLeiras)
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        Faj saved = service.save(data);
        assertEquals(hosszuLeiras, saved.getLeiras());
    }

    @Test
    @DisplayName("Az latinNev mező egyedi")
    void createUnique() {
        String latinNev = "Tyukusz Tyukusz";
        Faj data = Faj.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev(latinNev)
                .leiras("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        service.save(data);
        Faj data2 = Faj.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev(latinNev)
                .leiras("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();

        Exception exception = null;
        try {
            service.save(data2);
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
            Faj fajDataInit = Faj.builder()
                    .id(null)
                    .nev("Szürke farkas")
                    .latinNev("Canis lupus sp.")
                    .turesHatar(Tureshatar.GENERALISTA)
                    .leiras("Ordas Farkas")
                    .build();

            fajData = service.save(fajDataInit);
        }

        @AfterEach
        void tearDown() {
            service.deleteByIdIfExists(fajData.getId());
        }

        @Test
        @DisplayName("Elem lekérdezése id alapján")
        void getId() {
            Faj read = service.findById(fajData.getId());
            assertNotNull(read.getId());
            assertEquals("Szürke farkas", read.getNev());
            assertEquals("Canis lupus sp.", read.getLatinNev());
            assertEquals(Tureshatar.GENERALISTA, read.getTuresHatar());
        }

        @Test
        @DisplayName("Elem törlése")
        void delete() {
            Faj read = service.findById(fajData.getId());
            assertNotNull(read.getId());
            service.deleteByIdIfExists(fajData.getId());
            Exception exception = null;
            try {
                Faj readAfterDelete = service.findById(fajData.getId());
                assertNotNull(readAfterDelete.getLatinNev());
            } catch (Exception e) {
                exception = e;
            }
            assertNotNull(exception);
        }

        @Test
        @DisplayName("Elem frissítése")
        void update() {
            Faj data = service.getById(fajData.getId());
            data.setNev("Új név");
            Faj read = service.findById(data.getId());
            assertEquals("Új név", read.getNev());
        }
    }
}
