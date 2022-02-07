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
        Species data = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin("Tyukusz Udvarikusz")
                .description("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        Species saved = service.save(data);
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
        Species data = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin("Tyukusz sp. Magyarikusz")
                .description(hosszuLeiras)
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        Species saved = service.save(data);
        assertEquals(hosszuLeiras, saved.getDescription());
    }

    @Test
    @DisplayName("Az latinNev mező egyedi")
    void createUnique() {
        String latinNev = "Tyukusz Tyukusz";
        Species data = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin(latinNev)
                .description("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        service.save(data);
        Species data2 = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin(latinNev)
                .description("Tyúúúúk")
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

        private Species SpeciesData;

        @BeforeEach
        void setUp() {
            Species SpeciesDataInit = Species.builder()
                    .id(null)
                    .name("Szürke farkas")
                    .nameLatin("Canis lupus sp.")
                    .turesHatar(Tureshatar.GENERALISTA)
                    .description("Ordas Farkas")
                    .build();

            SpeciesData = service.save(SpeciesDataInit);
        }

        @AfterEach
        void tearDown() {
            service.deleteByIdIfExists(SpeciesData.getId());
        }

        @Test
        @DisplayName("Elem lekérdezése id alapján")
        void getId() {
            Species read = service.findById(SpeciesData.getId());
            assertNotNull(read.getId());
            assertEquals("Szürke farkas", read.getName());
            assertEquals("Canis lupus sp.", read.getNameLatin());
            assertEquals(Tureshatar.GENERALISTA, read.getTuresHatar());
        }

        @Test
        @DisplayName("Elem törlése")
        void delete() {
            Species read = service.findById(SpeciesData.getId());
            assertNotNull(read.getId());
            service.deleteByIdIfExists(SpeciesData.getId());
            Exception exception = null;
            try {
                Species readAfterDelete = service.findById(SpeciesData.getId());
                assertNotNull(readAfterDelete.getNameLatin());
            } catch (Exception e) {
                exception = e;
            }
            assertNotNull(exception);
        }

        @Test
        @DisplayName("Elem frissítése")
        void update() {
            Species data = service.getById(SpeciesData.getId());
            data.setName("Új név");
            Species read = service.findById(data.getId());
            assertEquals("Új név", read.getName());
        }
    }
}
