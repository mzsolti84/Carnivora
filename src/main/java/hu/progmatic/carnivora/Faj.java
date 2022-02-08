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
public class Faj {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    @NotEmpty
    private String leiras;
    private String nev;
    @NotEmpty
    @Column(unique = true)
    private String latinNev;

    @Enumerated(EnumType.STRING)
    private TermeszetvedelmiStatusz statusz;

    @Enumerated(EnumType.STRING)
    private Tureshatar turesHatar;

    private String fotoURL;
    private String wikiURL;

    @ManyToOne
    Klad klad;
}
