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
public class Klad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nev;

    @Column(unique = true)
    private String latinNev;

    private String leiras;

    @ManyToOne
    private Klad parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Klad> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "klad", cascade = CascadeType.ALL)
    private List<Species> fajLista = new ArrayList<>();
}