package hu.progmatic.felhasznalo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FelhasznaloRepository extends JpaRepository<Felhasznalo, Long> {
  Optional<Felhasznalo> findByNev(String nev);
}
