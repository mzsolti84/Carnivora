package hu.progmatic.carnivora;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 20000)
    @NotEmpty
    private String description;
    private String name;
    @NotEmpty
    @Column(unique = true)
    private String nameLatin;

    @Enumerated(EnumType.STRING)
    private VeszelyeztetettKategoriak veszelyeztetettBesorolas;

    @Enumerated(EnumType.STRING)
    private Tureshatar turesHatar;

    private String fotoURL;
    private String wikiURL;

    @ManyToOne
    KladEntity clad;
}
