package hu.progmatic.carnivora;

import hu.progmatic.databaseinit.GetWholePathOfResource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FajlTest {
/*
    @Autowired
    private CarnivoraService service;
    private String file;

    @DisplayName("FajRecord betöltés")
    @BeforeEach
    void setUp() throws URISyntaxException {
        file = GetWholePathOfResource.getWholePath("faj.csv");
    }

    @Test
    @Disabled
    void throwExceptionOnInvalidName() {
        RuntimeException exception = null;
        try {
            service.databaseFactory("Nem Létező fájl");
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull(exception);
    }


    @Test
    @Disabled
    @DisplayName("Listába betölt teszt")
    void darabSzam() {
        List<Faj> ujFaj = service.databaseFactory(file);
        assertEquals(20, ujFaj.size());
    }

    @Test
    @Disabled
    @DisplayName("Helyes információ ellenőrizve")
    void factoryTest() {
        List<Faj> ujFaj = service.databaseFactory(file);
        assertEquals(15, ujFaj.get(3).getSzuloId());
        assertEquals("Canis lupus", ujFaj.get(3).getLatinNev());
        assertEquals("Szürke farkas", ujFaj.get(3).getNev());
        assertEquals(Tureshatar.GENERALISTA, ujFaj.get(3).getTuresHatar());
        assertEquals("Felis silvestris catus", ujFaj.get(4).getLatinNev());
        assertEquals("Prionodon pardicolor", ujFaj.get(5).getLatinNev());
        assertEquals("Foltos tigrispetymeg", ujFaj.get(5).getNev());
    }

 */
}