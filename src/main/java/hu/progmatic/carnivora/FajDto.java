package hu.progmatic.carnivora;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FajDto {
    private Integer id;
    private Integer szuloId;
    private String szuloNev;
    private String leiras;
    private String nev;
    private String latinNev;
    private VeszelyeztetettKategoriak veszelyeztetettBesorolas;
    private Tureshatar turesHatar;
    private String fotoURL;
    private String wikiURL;


}
