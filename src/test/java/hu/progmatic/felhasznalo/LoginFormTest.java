package hu.progmatic.felhasznalo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class LoginFormTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Jogosultság nélkül átirányít a login oldalra")
  void root() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
  }

  @Test
  @DisplayName("Jogosultság nélkül hozzáférünk a login képernyőhöz")
  void loginPage() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/login")
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("Sikeres bejelentkezéskor átirányít a kezdőlapra")
  void loginPost() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                    "username", "admin",
                    "password", "adminpass"
                ))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.forwardedUrl("/"));

  }

  @Nested
  @DisplayName("Bejelentkezett felhasználóval")
  @WithMockUser(username = "tesztfelhasznalonev")
  class BejelentkezveTest {

    @Test
    @DisplayName("Bejelentkezve hozzáférünk a kezdőlaphoz")
    void root() throws Exception {
      mockMvc.perform(
              MockMvcRequestBuilders.get("/")
          )
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(containsString("tesztfelhasznalonev")));
    }
  }

  private String buildUrlEncodedFormEntity(String... params) {
    if ((params.length % 2) > 0) {
      throw new IllegalArgumentException("Need to give an even number of parameters");
    }
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < params.length; i += 2) {
      if (i > 0) {
        result.append('&');
      }
      try {
        result.
            append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
            append('=').
            append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    return result.toString();
  }
}
