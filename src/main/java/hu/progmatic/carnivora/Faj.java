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
public class Faj {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer szuloId;
    private String szuloNev;
    @Column(length = 20000)
    @NotEmpty
    private String leiras;
    @NotEmpty
    private String nev;
    @NotEmpty
    @Column(unique = true)
    private String latinNev;
    @Enumerated(EnumType.STRING)
    private VeszelyeztetettKategoriak veszelyeztetettBesorolas;
    @Enumerated(EnumType.STRING)
    private Tureshatar turesHatar;
    private String fotoURL;
    private String wikiURL;
}
