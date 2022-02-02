package hu.progmatic.carnivoraProject;

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

    private FajRecord fajData;

    @BeforeEach
    void setUp() {
        fajData = service.getByLatinNev("Canis lupus");
        //fajData.setLatinNev("Canis lupus sp.");
    }

    //@AfterEach
    //void tearDown() {
    //    service.deleteByIdIfExists(fajData.getId());
    //}

    @Test
    @DisplayName("Elem létrehozása")
    void create() {
        FajRecord data = FajRecord.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        FajRecord saved = service.save(data);
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
        FajRecord data = FajRecord.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz sp. Magyarikusz")
                .leiras(hosszuLeiras)
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        FajRecord saved = service.save(data);
        assertEquals(hosszuLeiras, saved.getLeiras());
    }

    @Test
    //@Disabled
    @DisplayName("Az latinNev mező egyedi")
    void createUnique() {
        String latinNev = "Tyukusz Tyukusz";
        FajRecord data = FajRecord.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev(latinNev)
                .leiras("Tyúúúúk")
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        service.save(data);
        FajRecord data2 = FajRecord.builder()
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

    @Test
    @DisplayName("Elem lekérdezése id alapján")
    void getId() {
        FajRecord read = service.findById(fajData.getId());
        assertNotNull(read.getId());
        assertEquals("Szürke farkas", read.getNev());
        assertEquals("Canis lupus", read.getLatinNev());
        assertEquals(Tureshatar.GENERALISTA, read.turesHatar);
    }

    @Test
    @DisplayName("Elem törlése")
    void delete() {
        FajRecord read = service.findById(fajData.getId());
        assertNotNull(read.getId());
        service.deleteByIdIfExists(fajData.getId());
        Exception exception = null;
        try {
            FajRecord readAfterDelete = service.findById(fajData.getId());
            assertNotNull(readAfterDelete.getLatinNev());
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Elem frissítlése")
    void update() {
        FajRecord data = service.getById(fajData.getId());
        data.setNev("Új név");
        FajRecord read = service.findById(data.getId());
        assertEquals("Új név", read.getNev());
    }
}
