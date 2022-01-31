package hu.progmatic.varadiattila.CarnivoraRepoVA;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VAFaj {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fajNev;
    private String szuloNev;

    @ManyToOne
    private VAKlad klad;
}
