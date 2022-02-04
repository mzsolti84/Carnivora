package hu.progmatic.carnivora;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer ancestorId;
    private String ancestorName;
    @Column(length = 20000)
    @NotEmpty
    private String description;
    @NotEmpty
    private String name;
    @NotEmpty
    @Column(unique = true)
    private String nameLatin;
    @Enumerated(EnumType.STRING)
    private ConservationStatus conservationStatus;
    @Enumerated(EnumType.STRING)
    private Endurance environmentalEndurance;
    private String photURL;
    private String wikiURL;
}
