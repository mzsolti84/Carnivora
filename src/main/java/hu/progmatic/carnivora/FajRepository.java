package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FajRepository extends JpaRepository<Faj, Integer> {
    Faj getByLatinNev(String latinNev);
}
