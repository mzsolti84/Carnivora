package hu.progmatic.felhasznalo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitFelhasznalo {
    private String felhasznaloNev;
    private String email;
    private String jelszo;
    private String keresztNev;
    private String vezetekNev;
    private boolean engedelyezve;
    private UserType role;
}
