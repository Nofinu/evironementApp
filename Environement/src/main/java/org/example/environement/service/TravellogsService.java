package org.example.environement.service;

import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.entity.Travellog;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravellogsService {
    private TravellogRepository travellogRepository;
    private ObservationRepository observationRepository;

    public TravellogsService(TravellogRepository travellogRepository,ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.observationRepository = observationRepository;
    }

    public List<TravellogDtoResponse> get (){
        return travellogRepository.findAll().stream().map(Travellog::entityToDto).collect(Collectors.toList());
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
}
