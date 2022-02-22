package hu.progmatic.carnivora;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class FajDtoTest {

    @Test
    @DisplayName("Elem létrehozása FajDto")
    void create() {
        FajDto data = FajDto.builder()
                .id(null)
                .nev("Tyúk")
                .latinNev("Tyukusz Udvarikusz")
                .leiras("Tyúúúúk")
                .statusz(TermeszetvedelmiStatusz.HAZIASITOTT)
                .szuloId(4)
                .build();
        assertNotNull(data);
        assertNull(data.getId());
        assertEquals("Tyúk", data.getNev());
        assertEquals("Tyúúúúk", data.getLeiras());
        assertEquals("Tyukusz Udvarikusz", data.getLatinNev());
        assertEquals("Háziasított", data.getStatusz().getDisplayName());
        assertEquals(4, data.getSzuloId());
    }
}