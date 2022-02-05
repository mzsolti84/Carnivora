package hu.progmatic.klad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Species getByName(String name);
}
