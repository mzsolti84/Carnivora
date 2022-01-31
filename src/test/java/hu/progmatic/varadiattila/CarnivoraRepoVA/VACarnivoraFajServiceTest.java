package hu.progmatic.varadiattila.CarnivoraRepoVA;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VACarnivoraFajServiceTest {

    @Autowired
    VACarnivoraFajService fajService;


    @Test
    @DisplayName("1feladat - Létrehozás")
    void create(){
        VAKlad klad = fajService.createKlad(
                VAKlad.builder()
                        .kladName("Házimacska")
                        .build()
        );
        fajService.createFaj(
                VAFaj.builder()
                        .fajNev("Egyiptomi Kopasz Macska")
                        .szuloNev(klad.getKladName())
                        .klad(klad)
                        .build()
        );
        fajService.createFaj(
                VAFaj.builder()
                        .fajNev("Maine Coon")
                        .szuloNev(klad.getKladName())
                        .klad(klad)
                        .build()
        );
        assertEquals("Házimacska",fajService.getParentNameById(klad.getId()));
        assertThat(fajService.getFajByKladId(klad.getId()))
                .extracting(VAFaj::getFajNev)
                .containsExactlyInAnyOrder("Egyiptomi Kopasz Macska","Maine Coon");
    }

    @Nested
    class StruktúraTest{
        VAKlad klad;
        VAFaj faj1;
        VAFaj faj2;
        boolean deleteWhenTearDown = true;


        @BeforeEach
        void setUp(){
            klad = fajService.createKlad(
                    VAKlad.builder()
                            .kladName("Házimacska")
                            .build()
            );
            faj1 = fajService.createFaj(
                    VAFaj.builder()
                            .fajNev("Egyiptomi Kopasz Macska")
                            .szuloNev(klad.getKladName())
                            .klad(klad)
                            .build()
            );
            faj2 = fajService.createFaj(
                    VAFaj.builder()
                            .fajNev("Maine Coon")
                            .szuloNev(klad.getKladName())
                            .klad(klad)
                            .build());
        }

        @AfterEach
        void tearDown(){
            if(deleteWhenTearDown){
                fajService.deleteFaj(faj1);
                fajService.deleteFaj(faj2);
                fajService.deleteKlad(klad);
            }
        }


        @Test
        @DisplayName("2feladat - DTO rendben van")
        void getDtoStructure(){
            VAKladDto dto = fajService.getFullDto(klad.getId());
            assertEquals("Házimacska",dto.getKladName());
            assertThat(dto.getFajok()).extracting(VAFajDto::getName)
                    .containsExactlyInAnyOrder("Egyiptomi Kopasz Macska","Maine Coon");
        }

        @Test
        @DisplayName("3feladat - Teljes törlés")
        void deleteAll(){
            fajService.deleteAll(klad);
            assertFalse(fajService.kladExistById(klad.getId()));
            deleteWhenTearDown = false;
        }

    }
}