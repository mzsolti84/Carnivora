package hu.progmatic.felhasznalo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
  private String felhasznaloNev;

  @Column(nullable = false)
  private String keresztNev;

  @Column(nullable = false)
  private String vezetekNev;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String jelszo;

  @Builder.Default
  private boolean engedelyezve = false;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private UserType role = UserType.USER;
}