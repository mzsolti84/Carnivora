package hu.progmatic.felhasznalo.token;


import hu.progmatic.felhasznalo.Felhasznalo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MegerositoToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String token;

    @ManyToOne
    Felhasznalo felhasznalo;

}
