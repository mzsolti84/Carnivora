package hu.progmatic.felhasznalo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UjFelhasznaloCommand {
  @NotEmpty(message = "A felhasználnév megadása kötelező!")
  private String felhasznaloNev;
  @NotEmpty(message = "A keresztnén megadása kötelező!")
  private String keresztNev;
  @NotEmpty(message = "A vezetéknév megadása kötelező!")
  private String vezetekNev;
  @Email(message = "Az email formátuma nem megfelelő!")
  @NotEmpty(message ="Az email megadása kötelező!")
  private String email;
  @NotEmpty(message = "Add meg a jelszót!")
  private String jelszo;
}


