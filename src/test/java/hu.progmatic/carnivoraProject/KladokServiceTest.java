package hu.progmatic.carnivoraProject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class KladokServiceTest {
    @Autowired
    private KladokService service;

    @Test
    @DisplayName("Elem létrehozása, és visszaolvasása")
    void create() {
        Klad data = Klad.builder()
                .nev("1")
                .build();
        Klad input = service.save(data);
        assertEquals("1", input.getNev());

    }

    @Test
    @DisplayName("Ősosztály gyerek nélkül")
    void gyerekNelkuliOsOsztaly() {
        service.save(Klad.builder().nev("Ősosztály").szulo(null).build());
        List<Klad> gyerekNelkuli = service.findAllWithNoChild();
        assertThat(gyerekNelkuli)
                .extracting(Klad::getNev)
                .containsExactly("cibetmacskafélék családja","Ősosztály");
    }

    @Test
    @DisplayName("Gyerek megtalálása egy fa esetén")
    void gyerekNelkuli() {
        Klad os = service.save(Klad.builder().nev("Ős").build());
        Klad k1 = service.save(Klad.builder().nev("K1").szulo(os).build());
        service.save(Klad.builder().nev("K2").szulo(k1).build());
        List<Klad> gyerekNelkuli = service.findAllWithNoChild();
        assertThat(gyerekNelkuli)
            .extracting(Klad::getNev)
            .containsExactly("cibetmacskafélék családja","K2");
    }

    @Test
    @DisplayName("Gyerek megtalálása több fa esetén")
    void tobbGyerekNelkuli() {
        Klad os1 = service.save(Klad.builder().nev("Ős1").build());
        Klad k11 = service.save(Klad.builder().nev("K11").szulo(os1).build());
        service.save(Klad.builder().nev("K12").szulo(k11).build());

        Klad os2 = service.save(Klad.builder().nev("Ős1").build());
        Klad k21 = service.save(Klad.builder().nev("K21").szulo(os2).build());
        Klad k22 = service.save(Klad.builder().nev("K22").szulo(k21).build());
        service.save(Klad.builder().nev("K23").szulo(k22).build());

        List<Klad> gyerekNelkuli = service.findAllWithNoChild();
        assertThat(gyerekNelkuli)
                .extracting(Klad::getNev)
                .containsExactly("cibetmacskafélék családja","K12","K23");
    }

    @Disabled
    @Test
    @DisplayName("Proba adatok")
    void proba() {
        Klad o1 = service.save(Klad.builder().nev("Ős-macskaformák alcsaládja").build());
        Klad k1 = service.save(Klad.builder().nev("párducformák alcsaládja").szulo(o1).build());
        Klad k2 = service.save(Klad.builder().nev("mongúzfélék családja").szulo(k1).build());
        Klad k3 = service.save(Klad.builder().nev("hiénafélék családja").szulo(k2).build());
        Klad k4 = service.save(Klad.builder().nev("cibetmacskafélék családja").szulo(k3).build());

        o1 = service.save(Klad.builder().nev("Ős-kutyafélék családja").build());
        k1 = service.save(Klad.builder().nev("bűzösborzfélék családja").szulo(o1).build());
        k2 = service.save(Klad.builder().nev("vidraformák alcsaládja").szulo(k1).build());
        k3 = service.save(Klad.builder().nev("borzformák alcsaládja").szulo(k2).build());

        List<Klad> gyerekNelkuli = service.findAllWithNoChild();
        assertThat(gyerekNelkuli)
                .extracting(Klad::getNev)
                .containsExactly("cibetmacskafélék családja","borzformák alcsaládja");
    }

}