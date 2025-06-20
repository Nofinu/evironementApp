package org.example.environement.controller;

import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.service.TravellogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/travellog")
public class TravellogController {

    private final TravellogsService travellogsService;

    public TravellogController(TravellogsService travellogsService) {
        this.travellogsService = travellogsService;
    }

    @GetMapping
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs (){
        return ResponseEntity.ok(travellogsService.get());
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<TravellogDtoStat> getStatFromObseration (@PathVariable long id){
        return ResponseEntity.ok(travellogsService.getStat(id));
    }
}
