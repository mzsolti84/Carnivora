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
public class FajRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer szuloId;
    String szuloNev;
    @Column(length = 20000)
    @NotEmpty
    String leiras;
    @NotEmpty
    String nev;
    @NotEmpty
    @Column(unique = true)
    String latinNev;
    @Enumerated(EnumType.STRING)
    VeszelyeztetettKategoriak veszelyeztetettBesorolas;
    @Enumerated(EnumType.STRING)
    Tureshatar turesHatar;
    String fotoURL;
    String wikiURL;
}
