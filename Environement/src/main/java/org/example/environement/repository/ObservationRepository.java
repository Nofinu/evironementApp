package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long> {
    public List<Observation> findObservationByLocationIsLike (String location);
    public List<Observation> findObservationBySpecieId(long id);
}
