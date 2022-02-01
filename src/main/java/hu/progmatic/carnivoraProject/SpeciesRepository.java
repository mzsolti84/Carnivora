package hu.progmatic.carnivoraProject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<FajRecord, Integer> {
    FajRecord getByLatinNev(String latinNev);
}
