package hu.progmatic.carnivoraProject;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProbaKlad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NotEmpty
    String nev;
    String szuloNev;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn
    ProbaKlad szulo;
}
