package com.discovery.controller;

import com.discovery.entity.PlanetEntity;
import com.discovery.mapper.PlanetMapper;
import com.discovery.model.PlanetModel;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlanetController {
    private PlanetMapper planetMapper
            = Mappers.getMapper(PlanetMapper.class);
    private final PlanetRepository planetRepository;

    @GetMapping
    public List<PlanetModel> getAll() {
        List<PlanetModel> planetModelList = new ArrayList<>();
        planetRepository.findAll().forEach(x -> {
            planetModelList.add(planetMapper.entityToModel(x));
        });
        return planetModelList;
    }

    @PostMapping
    public void insertRecord() {
        PlanetEntity newPlanetEntity = new PlanetEntity();
        newPlanetEntity.setOrigin("A");
        newPlanetEntity.setDestination("B");
        planetRepository.save(newPlanetEntity);
    }
}
