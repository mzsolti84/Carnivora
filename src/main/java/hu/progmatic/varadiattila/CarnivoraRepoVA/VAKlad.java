package hu.progmatic.varadiattila.CarnivoraRepoVA;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VAKlad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String kladName;
    private String szuloNev;

    @Builder.Default
    @OneToMany(mappedBy = "klad", cascade = CascadeType.ALL)
    private List<VAFaj> fajok = new ArrayList<>();

}
