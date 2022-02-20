package hu.progmatic.carnivora.kepkezeles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KepRepository extends JpaRepository<Kep, Integer> {
    Kep getByMegnevezesIgnoreCase(String megnevezes);
}
