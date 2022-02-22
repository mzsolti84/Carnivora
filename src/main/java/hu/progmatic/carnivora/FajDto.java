package hu.progmatic.carnivora;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FajDto {

    private Integer id;
    @NotEmpty(message = "Leírás megadása kötelező!")
    private String leiras;
    @NotEmpty(message = "Magyar név megadása kötelező!")
    private String nev;
    @NotEmpty(message = "Latin név megadása kötelező!")
    private String latinNev;
    @Enumerated(EnumType.STRING)
    private TermeszetvedelmiStatusz statusz;
    @Enumerated(EnumType.STRING)
    private Tureshatar turesHatar;
    private String fotoURL;
    private String wikiURL;

    private String szuloNev;
    private Integer szuloId;

    private Integer kepId;

}
