package hu.progmatic.carnivora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FajDto {

    private Integer id;
    private String leiras;
    private String nev;
    private String latinNev;
    private TermeszetvedelmiStatusz statusz;
    private Tureshatar turesHatar;
    private String fotoURL;
    private String wikiURL;

    private String szuloNev;
    private Integer szuloId;

}
