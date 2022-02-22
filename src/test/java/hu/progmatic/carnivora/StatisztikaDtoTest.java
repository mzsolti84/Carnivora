package hu.progmatic.carnivora;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisztikaDtoTest {

    @Test
    @DisplayName("Stat konst. teszt")
    void create_cons() {
        StatisztikaDto data = new StatisztikaDto(30,20,0);

        assertNotNull(data);
        assertEquals(30, data.getFajDb());
        assertEquals(20, data.getKladDb());
        assertEquals(0, data.getKepDb());
    }

    @Test
    @DisplayName("Elem létrehozása Stat")
    void create() {

        StatisztikaDto data = StatisztikaDto.builder()
                .fajDb(30)
                .kladDb(28)
                .kepDb(4)
                .build();
        assertNotNull(data);
        assertEquals(30, data.getFajDb());
        assertEquals(28, data.getKladDb());
        assertEquals(4, data.getKepDb());

    }

}