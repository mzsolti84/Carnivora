package hu.progmatic.carnivora;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WithUserDetails("ADMIN")
class CarnivoraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Kezdőlap indítása megjelenik")
    void kezdolap() throws Exception {
        mockMvc.perform(get("/kezdolap"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Carnivora Project")));
    }

}