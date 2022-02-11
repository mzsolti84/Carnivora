package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FajRepository extends JpaRepository<Faj, Integer> {
    Faj getByNev(String nev);
    Faj getByNevContains(String nevReszlet);
}
