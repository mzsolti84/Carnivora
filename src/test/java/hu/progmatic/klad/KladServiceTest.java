package hu.progmatic.klad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KladServiceTest {

    @Autowired
    private KladService service;

    @Test
    void iniTeszt() {
        KladWithChildrenDto motherKlad = service.getKladDtoByName("macskaalkatúak alrendje");

        assertThat(motherKlad.getChildren())
                .extracting(KladWithChildrenDto::getName)
                .containsExactlyInAnyOrder("madagaszkári cibetmacskafélék családja",
                        "macskafélék családja",
                        "mongúzfélék családja",
                        "hiénafélék családja",
                        "cibetmacskafélék családja",
                        "pálmacibetfélék családja");
    }
}