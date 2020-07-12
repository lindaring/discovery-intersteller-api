package com.discovery.controller;

import com.discovery.entity.PlanetEntity;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlanetController {
    private final PlanetRepository planetRepository;

    @GetMapping
    public Iterable<PlanetEntity> getAll() {
        return planetRepository.findAll();
    }

    @PostMapping
    public void insertRecord() {
        PlanetEntity newPlanetEntity = new PlanetEntity();
        newPlanetEntity.setOrigin("A");
        newPlanetEntity.setDestination("B");
        planetRepository.save(newPlanetEntity);
    }
}
