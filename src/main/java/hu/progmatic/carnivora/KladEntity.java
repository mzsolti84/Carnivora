package hu.progmatic.carnivora;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KladEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String latinName;

    private String description;

    @ManyToOne
    private KladEntity parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<KladEntity> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clad", cascade = CascadeType.ALL)
    private List<Species> speciesList = new ArrayList<>();
}