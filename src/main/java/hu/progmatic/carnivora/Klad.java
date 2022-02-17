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
    @Lob
    private String leiras;
    @ManyToOne
    private Klad szulo;

    private Integer kepId;

    @Builder.Default
    @OneToMany(mappedBy = "szulo", cascade = CascadeType.ALL)
    private List<Klad> leszarmazottak = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "klad", cascade = CascadeType.ALL)
    private List<Faj> fajLista = new ArrayList<>();
}