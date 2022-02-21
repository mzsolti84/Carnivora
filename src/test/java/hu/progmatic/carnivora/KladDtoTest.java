package hu.progmatic.carnivora;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KladDtoTest {
    @Test
    @DisplayName("Elem létrehozása KladDto")
    void create() {
        KladDto data = KladDto.builder()
                .id(null)
                .nev("Tyúkféle")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .szuloId(0)
                .szuloNev("Madarak rendje")
                .build();

        assertNotNull(data);
        assertNull(data.getId());
        assertEquals("Tyúkféle", data.getNev());
        assertEquals("Tyúúúúk", data.getLeiras());
        assertEquals("Tyukusz Udvarikusz", data.getLatinNev());
        assertEquals("Madarak rendje", data.getSzuloNev());
        assertEquals(0, data.getSzuloId());
    }
}