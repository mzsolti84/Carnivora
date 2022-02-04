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

    @Autowired
    private CarnivoraService service;
    private String file;

    @DisplayName("FajRecord betöltés")
    @BeforeEach
    void setUp() throws URISyntaxException {
        file = GetWholePathOfResource.getWholePath("faj.csv");
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
    @DisplayName("Listába betölt teszt")
    void darabSzam() {
        List<FajRecord> ujFaj = service.databasefactory(file);
        assertEquals(20, ujFaj.size());
    }

    @Test
    @DisplayName("Helyes információ ellenőrizve")
    void factoryTest() {
        List<FajRecord> ujFaj = service.databasefactory(file);
        assertEquals(15, ujFaj.get(3).szuloId);
        assertEquals("Canis lupus", ujFaj.get(3).latinNev);
        assertEquals("Szürke farkas", ujFaj.get(3).nev);
        assertEquals(Tureshatar.GENERALISTA, ujFaj.get(3).turesHatar);
        assertEquals("Felis silvestris catus", ujFaj.get(4).latinNev);
        assertEquals("Prionodon pardicolor", ujFaj.get(5).latinNev);
        assertEquals("Foltos tigrispetymeg", ujFaj.get(5).nev);
    }
}