package hu.progmatic.carnivora;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KladRepository extends JpaRepository<KladEntity, Integer> {
    KladEntity findByNameEquals(String name);

    @Query(
            """
                    select k
                    from KladEntity as k
                    left join KladEntity as child on child.parent = k
                    where child is null
                    """
    )
    List<KladEntity> findAllWithNoChild();
}
