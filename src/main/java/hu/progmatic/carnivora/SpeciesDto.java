package hu.progmatic.carnivora;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder

public class SpeciesDto {
    private Integer id;
    private Integer ancestorId;
    private String ancestorName;
    @NotEmpty
    private String description;
    @NotEmpty
    private String name;
    @NotEmpty
    private String nameLatin;
    private ConservationStatus endangeredClassification;
    private Endurance environmentalEndurance;
    private String photURL;
    private String wikiURL;
}
