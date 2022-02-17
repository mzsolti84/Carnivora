package hu.progmatic.service;

import hu.progmatic.carnivora.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KladServiceTest {

    @Autowired
    private KladService kladService;
    @Autowired
    private FajService fajService;

    @Test
    void iniTeszt() {
        KladWithChildrenDto motherKlad = kladService.getKladDtoByName("macskaalkatúak alrendje");

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
        List<KladWithChildrenDto> dtoNoCildList = kladService.findAllWithNoChild();
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

    @Test
    @DisplayName("Két Faj legközelebbi közös Klád ősének latin nevét megkeresi")
    void findFirstCommonKladAncestorTest() {
        KladDto dto = kladService.buildKladDtoFromKlad(
                kladService.getFirstCommonKladAncestorOfFaj(fajService.getByNev("Fossza"), fajService.getByNev("Falanuk")));
        assertEquals("Eupleridae", dto.getLatinNev());

        dto = kladService.buildKladDtoFromKlad(
                kladService.getFirstCommonKladAncestorOfFaj(fajService.getByNev("Európai nyérc"), fajService.getByNev("Házimacska")));
        assertEquals("Carnivora", dto.getLatinNev());
    }
}