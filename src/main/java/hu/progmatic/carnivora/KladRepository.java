package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KladRepository extends JpaRepository<Klad, Integer> {
    Klad findByNameEquals(String name);

    @Query(
            """
                    select k
                    from Klad as k
                    left join Klad as child on child.parent = k
                    where child is null
                    """
    )
    List<Klad> findAllWithNoChild();
}
