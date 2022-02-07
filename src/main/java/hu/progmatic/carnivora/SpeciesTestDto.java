package hu.progmatic.carnivora;

import hu.progmatic.carnivora.Tureshatar;
import hu.progmatic.carnivora.VeszelyeztetettKategoriak;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeciesTestDto {

    private String name;
    private String parentName;
    private String description;
    private VeszelyeztetettKategoriak veszelyeztetettBesorolas;
    private Tureshatar turesHatar;
}
