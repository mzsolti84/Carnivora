package hu.progmatic.felhasznalo;

import hu.progmatic.felhasznalo.token.MegerositoToken;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

  private LocalDateTime regisztracioIdeje;

  private LocalDateTime megerositesIdeje;

  @OneToOne (cascade = CascadeType.ALL)
  private MegerositoToken token;

}