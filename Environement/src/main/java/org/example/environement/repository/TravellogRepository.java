package org.example.environement.repository;

import org.example.environement.entity.Observation;
import org.example.environement.entity.Travellog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravellogRepository extends JpaRepository<Travellog,Long> {
    public List<Travellog> findTravellogByObservation_Id (long id);
}
