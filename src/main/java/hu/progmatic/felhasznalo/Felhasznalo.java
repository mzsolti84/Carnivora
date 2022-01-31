package hu.progmatic.felhasznalo;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Felhasznalo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nev;

  private String jelszo;

  @Enumerated(EnumType.STRING)
  private UserType role;
}