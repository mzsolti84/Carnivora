package hu.progmatic.carnivora;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class CarnivoraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @WithUserDetails("admin")
    class AdminTest {
        @Test
        @DisplayName("Kezdőlap indítása megjelenik")
        void kezdolap() throws Exception {
            mockMvc.perform(get("/kezdolap"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Carnivora Project")));
        }

        @Test
        @DisplayName("Faj kereső indítása megjelenik")
        void carnivora() throws Exception {
            mockMvc.perform(get("/carnivora"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Keresés a faj adatbázisban")));
        }

        @Test
        @DisplayName("Bemutatkozás lap megjelenik")
        void about() throws Exception {
            mockMvc.perform(get("/about"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Projekt célja és alapfogalmak")));
        }

        @Test
        @DisplayName("Faj admin adatlista lap megjelenik")
        void fajAdatlista() throws Exception {
            mockMvc.perform(get("/faj_adatlista"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Cryptoprocta ferox")));
        }

        @Test
        @DisplayName("Új faj rögzítése sikeres")
        void ujFajInditasSikeres() throws Exception {
            mockMvc.perform(
                            post("/faj_adatszerk")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .content("id=&nev=Valami&latinNev=Valami&leiras=v&fotoURL=v&szuloId=4&statusz=KIHALT&turesHatar=SPECIALISTA"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Term.védelmi státusz")));
        }

        @Test
        @DisplayName("Faj törlése sikertelen")
        void fajTorles() throws Exception {
            mockMvc.perform(post("/faj_adatlista/delete/8"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("No class hu.progmatic.carnivora.Faj entity with id 8 exists!")));
        }

        @Test
        @DisplayName("Faj szerkesztés sikeres")
        void fajSzerkesztes() throws Exception {
            mockMvc.perform(post("/faj_adatszerk/15"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Faj adatbázis karbantartó")));
        }

        @Test
        @Disabled
        @DisplayName("Új faj elküldésekor megjelennek a hibaüzenetek")
        void UjfajHiba() throws Exception {
            mockMvc.perform(
                            post("/faj_adatszerk")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .content("id=&nev=&latinNev=&leiras=&fotoURL=&szuloId=&statusz=&turesHatar="))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Latin név megadása kötelező!")));
        }
    }

    @Nested
    @WithUserDetails("user")
    class UserTest {
        @Test
        @DisplayName("Felhasználók karbantartása hiba")
        void kezdolap() throws Exception {
            mockMvc.perform(get("/felhasznalo"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Rossz helyre tévedtél!")));
        }

        @Test
        @DisplayName("Faj kereső indítása megjelenik")
        void carnivora() throws Exception {
            mockMvc.perform(get("/carnivora"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Keresés a faj adatbázisban")));
        }

        @Test
        @DisplayName("Bemutatkozás lap megjelenik")
        void about() throws Exception {
            mockMvc.perform(get("/about"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Projekt célja és alapfogalmak")));
        }
    }

    @Nested
    class GuestTest {
        @Test
        @DisplayName("Felhasználók karbantartása megjelenik")
        void kezdolap() throws Exception {
            mockMvc.perform(get("/felhasznalo"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }

        @Test
        @DisplayName("Faj admin adatlista lap nem jelenik meg")
        void fajAdatlista() throws Exception {
            mockMvc.perform(get("/faj_adatlista"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }

        @Test
        @DisplayName("Faj kereső indítása megjelenik")
        void carnivora() throws Exception {
            mockMvc.perform(get("/carnivora"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Keresés a faj adatbázisban")));
        }

        @Test
        @DisplayName("Bemutatkozás lap megjelenik")
        void about() throws Exception {
            mockMvc.perform(get("/about"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Projekt célja és alapfogalmak")));
        }

        @Test
        @DisplayName("Új faj rögzítése sikertelen")
        void ujFajInditasSikertelen() throws Exception {
            mockMvc.perform(
                            post("/faj_adatszerk")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .content("id=&nev=Valami&latinNev=Valami&leiras=v&fotoURL=v&szuloId=4&statusz=KIHALT&turesHatar=SPECIALISTA"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }
    }

}