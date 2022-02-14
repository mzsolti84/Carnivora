package hu.progmatic.felhasznalo.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MegerositoTokenRepository extends JpaRepository<MegerositoToken, Long> {
    Optional<MegerositoToken> findByToken(String token);
}
