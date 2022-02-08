package hu.progmatic.service;

import hu.progmatic.carnivora.FajService;
import hu.progmatic.carnivora.FajDto;
import hu.progmatic.carnivora.Tureshatar;
import hu.progmatic.carnivora.TermeszetvedelmiStatusz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FajServiceTest {


    @Autowired
    private FajService fajService;


    @Test
    void speciesRecordTest() {
        FajDto speciesTest = fajService.buildFajDtoByFajNev("Fossza");
        assertEquals("Madagaszkár legnagyobb ragadozó állata: a nőstény testhossza 65–70 centiméter, a hímé 75–80 centiméter, farokhossza 70–90 centiméter, a nőstény testtömege 5–7 kilogramm, a hímé 6–10 kilogramm. Különösen szembeötlővé válik ez a macskaszerűség, ha a fosszát a vele egyező színezetű, megnyúlt alakú, alacsony tairával (Eira barbara) vetjük össze. A hasonlóság miatt a rendszertani helye is hosszú ideig vitás volt.",
                speciesTest.getLeiras());
        assertEquals(TermeszetvedelmiStatusz.SEBEZHETO, speciesTest.getStatusz());
        assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());


        speciesTest = fajService.buildFajDtoByFajNev("Hosszúfarkú Vidra");
        assertEquals("A hosszúfarkú vidra átlag fej-testhossza 60-100 centiméter, farokhossza pedig 30-50 centiméter. Testtömege 5-15 kilogramm között lehet. A hímek nagyobbak a nőstényeknél. Ez a vidrafaj számos vizes helyen található meg. Általában a nagy folyók közelségét keresi. Egyaránt megél a lombhullató- és örökzöld erdőkben, a nagy mocsaras területeken, valamint a szavannákon is. Állományai nagyobbak és egészségesebbek a magasabban levő gyorsabb folyású folyókban és patakokban, mint az alföldek állóvizeiben. Fő táplálékai a halak, a kagylók és a puhatestűek, de rovarokat, madarakat, kisebb emlősöket és hüllőket is elfogyaszt. Magányosan él és vadászik.",
                speciesTest.getLeiras());
        assertEquals(TermeszetvedelmiStatusz.SULYOSAN_VESZELYEZTETETT, speciesTest.getStatusz());
        assertEquals(Tureshatar.GENERALISTA, speciesTest.getTuresHatar());
    }

    @Test
    void speciesParentCladTest() {
        FajDto speciesTest = fajService.buildFajDtoByFajNev("Fossza");
        assertEquals("madagaszkári cibetmacskafélék családja", speciesTest.getSzuloNev());

        speciesTest = fajService.buildFajDtoByFajNev("Kinkaju");
        assertEquals("mosómedvefélék családja", speciesTest.getSzuloNev());
    }


}