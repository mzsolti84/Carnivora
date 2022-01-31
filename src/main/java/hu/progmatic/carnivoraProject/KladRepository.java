package hu.progmatic.carnivoraProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KladRepository extends JpaRepository<Klad, Integer> {
  @Query(
      """
          select k
          from KladEntity as k
          left join KladEntity as child on child.szulo = k
          where child is null
          """
  )
  List<Klad> findAllWithNoChild();
}
