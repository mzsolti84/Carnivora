package hu.progmatic.carnivoraProject;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FajRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer szuloId;
    String szuloNev;
    @Lob
    @NotEmpty
    String leiras;
    @NotEmpty
    String nev;
    @NotEmpty
    String latinNev;
    @Enumerated(EnumType.STRING)
    VeszelyeztetettKategoriak veszelyeztetettBesorolas;
    @Enumerated(EnumType.STRING)
    Tureshatar turesHatar;
    String fotoURL;
    String wikiURL;
}
