package hu.progmatic.felhasznalo;

import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log
@Transactional
public class FelhasznaloService implements InitializingBean {

  private final FelhasznaloRepository felhasznaloRepository;
  private final PasswordEncoder encoder;

  public FelhasznaloService(FelhasznaloRepository felhasznaloRepository, PasswordEncoder encoder) {
    this.felhasznaloRepository = felhasznaloRepository;
    this.encoder = encoder;
  }

  @RolesAllowed(UserType.Roles.USER_READ_ROLE)
  public List<Felhasznalo> findAll() {
    return felhasznaloRepository.findAll();
  }

  @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
  public void add(UjFelhasznaloCommand command) {
    if (felhasznaloRepository.findByNev(command.getNev()).isPresent()) {
      throw new FelhasznaloLetrehozasException("Ilyen névvel már létezik felhasználó!");
    }
    Felhasznalo felhasznalo = Felhasznalo.builder()
        .nev(command.getNev())
        .jelszo(encoder.encode(command.getJelszo()))
        .role(command.getRole())
        .build();
    felhasznaloRepository.save(felhasznalo);
  }

  @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
  public void delete(Long id) {
    felhasznaloRepository.deleteById(id);
  }

  @RolesAllowed(UserType.Roles.USER_READ_ROLE)
  public Optional<Felhasznalo> findByName(String nev) {
    return felhasznaloRepository.findByNev(nev);
  }

  @Override
  public void afterPropertiesSet() {
    if (findAll().isEmpty()) {
      add(new UjFelhasznaloCommand("admin", "adminpass", UserType.ADMIN));
      add(new UjFelhasznaloCommand("user", "user", UserType.USER));
      add(new UjFelhasznaloCommand("guest", "guest", UserType.GUEST));
    }
  }

  public boolean hasRole(String role) {
    MyUserDetails userPrincipal = getMyUserDetails();
    return userPrincipal.getRole().hasRole(role);
  }

  public Long getFelhasznaloId() {
    MyUserDetails userPrincipal = getMyUserDetails();
    return userPrincipal.getFelhasznaloId();
  }

  private MyUserDetails getMyUserDetails() {
    return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
