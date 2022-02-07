package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Species getByNev(String name);
}
