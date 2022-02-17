package hu.progmatic.service;

import hu.progmatic.carnivora.*;
import org.junit.jupiter.api.Disabled;
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
        KladDto dto = getFirstCommonKladAncestorDto(getFajByNev("Fossza"), getFajByNev("Falanuk"));
        assertEquals("Eupleridae", dto.getLatinNev());

        dto = getFirstCommonKladAncestorDto(getFajByNev("Falanuk"), getFajByNev("Fossza"));
        assertEquals("Eupleridae", dto.getLatinNev());

        dto = getFirstCommonKladAncestorDto(getFajByNev("Európai nyérc"), getFajByNev("Házimacska"));
        assertEquals("Carnivora", dto.getLatinNev());

        dto = getFirstCommonKladAncestorDto(getFajByNev("Házimacska"), getFajByNev("Európai nyérc"));
        assertEquals("Carnivora", dto.getLatinNev());

        dto = getFirstCommonKladAncestorDto(getFajByNev("Kinkaju"), getFajByNev("Európai nyérc"));
        assertEquals("Caniformia", dto.getLatinNev());

        dto = getFirstCommonKladAncestorDto(getFajByNev("Európai nyérc"), getFajByNev("Kinkaju"));
        assertEquals("Caniformia", dto.getLatinNev());
    }

    private KladDto getFirstCommonKladAncestorDto(Faj faj1, Faj faj2) {
        return kladService.getFirstCommonKladAncestorOfFaj(faj1, faj2);
    }

    private Faj getFajByNev(String nev) {
        return fajService.getByNev(nev);
    }
}