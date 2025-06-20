package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long>  {
    public List<Observation> findObservationByLocationIsLike (String location);
    public List<Observation> findObservationBySpecieId(long id);

    @Query("select o from Observation o where o.observerName = ?1 and o.observationDate > ?2")
    public List<Observation> findAllObservationForUserOnLastMonth (String user, LocalDate date);
}
