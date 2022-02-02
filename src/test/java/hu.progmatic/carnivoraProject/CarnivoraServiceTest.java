package hu.progmatic.carnivoraProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

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
    }

    @AfterEach
    void tearDown() {
        service.deleteByIdIfExists(fajData.getId());
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
                .latinNev("Tyúkusz")
                .leiras(hosszuLeiras)
                .veszelyeztetettBesorolas(VeszelyeztetettKategoriak.HAZIASITOTT)
                .build();
        FajRecord saved = service.save(data);
        assertEquals(hosszuLeiras, saved.getLeiras());
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

}
