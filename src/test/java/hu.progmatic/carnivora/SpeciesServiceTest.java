package hu.progmatic.carnivora;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpeciesServiceTest {
    @Autowired
    SpeciesService speciesService;

    @Test
    @DisplayName("Elem létrehozása")
    void create() {
        Species data = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin("Tyukusz Udvarikusz")
                .description("Tyúúúúk")
                .conservationStatus(ConservationStatus.HAZIASITOTT)
                .build();
        Species saved = speciesService.save(data);
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
                .conservationStatus(ConservationStatus.HAZIASITOTT)
                .build();
        Species saved = speciesService.save(data);
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
                .conservationStatus(ConservationStatus.HAZIASITOTT)
                .build();
        speciesService.save(data);
        Species data2 = Species.builder()
                .id(null)
                .name("Tyúk")
                .nameLatin(latinNev)
                .description("Tyúúúúk")
                .conservationStatus(ConservationStatus.HAZIASITOTT)
                .build();

        Exception exception = null;
        try {
            speciesService.save(data2);
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

        private Species speciesData;

        @BeforeEach
        void setUp() {
            Species speciesDataInit = Species.builder()
                    .id(null)
                    .name("Szürke farkas")
                    .nameLatin("Canis lupus sp.")
                    .environmentalEndurance(Endurance.GENERALISTA)
                    .description("Ordas Farkas")
                    .build();

            speciesData = speciesService.save(speciesDataInit);
        }

        @AfterEach
        void tearDown() {
            speciesService.deleteByIdIfExists(speciesData.getId());
        }

        @Test
        @DisplayName("Elem lekérdezése id alapján")
        void getId() {
            Species read = speciesService.findById(speciesData.getId());
            assertNotNull(read.getId());
            assertEquals("Szürke farkas", read.getName());
            assertEquals("Canis lupus sp.", read.getNameLatin());
            assertEquals(Endurance.GENERALISTA, read.getEnvironmentalEndurance());
        }

        @Test
        @DisplayName("Elem törlése")
        void delete() {
            Species read = speciesService.findById(speciesData.getId());
            assertNotNull(read.getId());
            speciesService.deleteByIdIfExists(speciesData.getId());
            Exception exception = null;
            try {
                Species readAfterDelete = speciesService.findById(speciesData.getId());
                assertNotNull(readAfterDelete.getNameLatin());
            } catch (Exception e) {
                exception = e;
            }
            assertNotNull(exception);
        }

        @Test
        @Disabled // EZT A TESZTET DTO-VAL KELL ÁTÍRNI, MERT ÍGY NEM TUD MŰKÖDNI
        @DisplayName("Elem frissítése")
        void update() {
            assertEquals("Új név", speciesService.getById(speciesData.getId()).getName());
        }
    }
}
