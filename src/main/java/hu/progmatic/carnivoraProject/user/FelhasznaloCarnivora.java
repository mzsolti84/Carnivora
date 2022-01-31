package hu.progmatic.carnivoraProject.user;



import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FelhasznaloCarnivora {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nev;

    private String jelszo;
    @Enumerated(EnumType.STRING)
    private UserTypeCarnivora role;
}
