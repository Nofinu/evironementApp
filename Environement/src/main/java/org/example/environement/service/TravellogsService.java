package org.example.environement.service;

import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.entity.Observation;
import org.example.environement.entity.Travellog;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravellogsService {
    private final TravellogRepository travellogRepository;
    private final ObservationRepository observationRepository;

    public TravellogsService(TravellogRepository travellogRepository,ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.observationRepository = observationRepository;
    }

    public List<TravellogDtoResponse> get (int pagesize){
        return travellogRepository.findAll(Pageable.ofSize(pagesize)).stream().map(Travellog::entityToDto).collect(Collectors.toList());
    }

    public TravellogDtoStat getStat (long idObservation){
        TravellogDtoStat travellogDtoStat = new TravellogDtoStat();
        travellogRepository.findTravellogByObservation_Id(idObservation).forEach((t)->{
            travellogDtoStat.addTotalEmissionsKg(t.getEstimatedCo2Kg());
            travellogDtoStat.addTotalDistanceKm(t.getDistanceKm());
            travellogDtoStat.addMode(t.getMode().toString(),t.getDistanceKm());
        });
        return travellogDtoStat;
    }

    public Map<String, TravellogDtoStat> getStatForUserLastMonth (String user){
        LocalDate dateMonthAgo = LocalDate.now().minusMonths(1);
        List<Observation> observations = observationRepository.findAllObservationForUserOnLastMonth(user,dateMonthAgo);

        Map<String, TravellogDtoStat> travellogDtoStatMap = new HashMap<>();

        for (Observation o : observations){
            TravellogDtoStat travellogDtoStat = new TravellogDtoStat();
            o.getTravellogs().forEach(tl -> {
                travellogDtoStat.addTotalEmissionsKg(tl.getEstimatedCo2Kg());
                travellogDtoStat.addTotalDistanceKm(tl.getDistanceKm());
                travellogDtoStat.addMode(tl.getMode().toString(),tl.getEstimatedCo2Kg());
            });
            travellogDtoStatMap.put(o.getLocation()+" : "+o.getObservationDate().toString(),travellogDtoStat);
        }

        return travellogDtoStatMap;
    }
}
