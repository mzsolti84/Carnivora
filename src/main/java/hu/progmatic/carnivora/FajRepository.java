package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FajRepository extends JpaRepository<Faj, Integer> {
    Faj getByNev(String nev);
    List<Faj> getByNevContains(String nevReszlet);
}
