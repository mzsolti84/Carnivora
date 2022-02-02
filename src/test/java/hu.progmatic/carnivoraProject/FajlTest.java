package hu.progmatic.carnivoraProject;

import org.junit.jupiter.api.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FajlTest {
    CarnivoraService service;
    String file;

    @DisplayName("FajRecord betöltés")
    @BeforeEach
    void setUp() throws URISyntaxException {
        file = TesztFileTeljesNev.getTeljesNev("TesztCsv/AdatbazisTeszt.csv");
    }

    @Test
    void throwExceptionOnInvalidName() {
        RuntimeException exception = null;
        try {
            service.databasefactory("Nem Létező fájl");
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull(exception);
    }


    @Test
    void darabSzam() {
        List<FajRecord> ujFaj = service.databasefactory(file);
        assertEquals(20, ujFaj.size());
    }


}
