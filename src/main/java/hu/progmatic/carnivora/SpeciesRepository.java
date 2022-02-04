package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<FajRecord, Integer> {
    FajRecord getByLatinNev(String latinNev);
}
