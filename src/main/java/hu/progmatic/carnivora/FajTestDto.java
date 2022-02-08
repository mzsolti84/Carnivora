package hu.progmatic.carnivora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FajTestDto {

    private String nev;
    private String szuloNev;
    private String leiras;
    private TermeszetvedelmiStatusz veszelyeztetettBesorolas;
    private Tureshatar turesHatar;
}
