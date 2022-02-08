package hu.progmatic.kladEsFaj;

import hu.progmatic.carnivora.KladService;
import hu.progmatic.carnivora.KladWithChildrenDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KladServiceTest {

    @Autowired
    private KladService service;

    @Test
    void iniTeszt() {
        KladWithChildrenDto motherKlad = service.getKladDtoByName("macskaalkatúak alrendje");

        assertThat(motherKlad.getLeszarmazott())
                .extracting(KladWithChildrenDto::getNev)
                .containsExactlyInAnyOrder("madagaszkári cibetmacskafélék családja",
                        "macskafélék családja",
                        "mongúzfélék családja",
                        "hiénafélék családja",
                        "cibetmacskafélék családja",
                        "pálmacibetfélék családja");
    }


    @Test
    void noChildTest() {
        List<KladWithChildrenDto> dtoNoCildList = service.findAllWithNoChild();
        assertThat(dtoNoCildList).extracting(KladWithChildrenDto::getNev).containsExactlyInAnyOrder(
                "madagaszkári cibetmacskafélék családja",
                "macskaformák alcsaládja",
                "párducformák alcsaládja",
                "kardfogú macskák alcsaládja – kihalt",
                "mongúzfélék családja",
                "hiénafélék családja",
                "cibetmacskafélék családja",
                "pálmacibetfélék családja",
                "macskamedvefélék családja",
                "kutyafélék családja",
                "bűzösborzfélék családja",
                "vidraformák alcsaládja",
                "borzformák alcsaládja",
                "méhészborzformák alcsaládja",
                "amerikai borzformák alcsaládja",
                "menyétformák alcsaládja",
                "mosómedvefélék családja",
                "medvefélék családja",
                "rozmárfélék családja",
                "fülesfókafélék családja",
                "valódi fókafélék családja"

        );
    }
}