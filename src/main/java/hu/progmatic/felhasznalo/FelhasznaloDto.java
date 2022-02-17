package hu.progmatic.felhasznalo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
public class FelhasznaloDto {

    private Long id;
    private String felhasznaloNev;
    private String keresztNev;
    private String vezetekNev;
    private String email;
    private String jelszo;
    private String engedelyezve;
    private UserType role;
    private String regisztracioIdeje;
    @Builder.Default
    private String megerositesIdeje = "Nincs";
}
