package org.example.environement.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.observation.ObservationDtoResponse;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Observation {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String observerName;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Double  latitude,longitude;
    @Column(nullable = false)
    private LocalDate observationDate;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    private Specie specie;

    @OneToMany(mappedBy = "observation", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Travellog> travellogs;


    public ObservationDtoResponse entityToDto (){
//        String strGoeJson = String.format("{\"type\": \"Point\",\"coordinates\": [%f,%f]}", this.getLongitude(), this.getLatitude());

        String geojson = "{"
                + "\"type\":\"Point\","
                + "\"coordinates\":[" + this.getLongitude() + "," + this.getLatitude() + "]"
                + "}";

        try{
            return ObservationDtoResponse.builder()
                    .observerName(this.getObserverName())
                    .location(this.getLocation())
                    .geoJsonUrl(STR."https://geojson.io/#data=data:application/json,\{URLEncoder.encode(geojson, "UTF-8").replace("+", "")}")
                    .observationDate(this.getObservationDate())
                    .comment(this.getComment())
                    .specie(this.getSpecie().entityToDto())
                    .travellogs(this.getTravellogs().stream().map(Travellog::entityToDto).collect(Collectors.toList()))
                    .build();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
