package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private double distanceKm;
    @Column(nullable = false)
    private TravelMode mode;
    @Column(nullable = false)
    private double estimatedCo2Kg;

    @ManyToOne
    @JoinColumn(name = "observation_id")
    private Observation observation;

    public void calculateCO2(){
        estimatedCo2Kg = switch (mode){
            case CAR -> distanceKm * 0.22;
            case BUS -> distanceKm * 0.11;
            case TRAIN -> distanceKm * 0.03 ;
            case PLANE -> distanceKm * 0.259;
            default -> 0.0;
        };
    }

    public TravellogDtoResponse entityToDto (){
        return TravellogDtoResponse.builder()
                .id(this.getId())
                .distanceKm(this.getDistanceKm())
                .mode(this.getMode().toString())
                .estimatedCo2Kg(this.getEstimatedCo2Kg())
                .build();
    }
}
