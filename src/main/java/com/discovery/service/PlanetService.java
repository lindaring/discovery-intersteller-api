package com.discovery.service;

import com.discovery.dto.Planet;
import com.discovery.dto.PlanetDto;
import com.discovery.entity.PlanetEntity;
import com.discovery.exception.PlanetNotFound;
import com.discovery.mapper.PlanetMapper;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetMapper planetMapper;

    public void persistPlanets(List<PlanetDto> planets) {
        planets.forEach(p -> {
            log.info("Persisting {} {} ...", p.getOrigin(), p.getDestination());

            // Ensure uppercase
            p.setOrigin(p.getOrigin().toUpperCase());
            p.setDestination(p.getDestination().toUpperCase());

            if (!p.getOrigin().equals(p.getDestination())) { // Avoid storing broken data
                //The parent
                PlanetEntity source = planetRepository
                        .findByShortName(p.getOrigin())
                        .stream()
                        .findFirst()
                        .orElse(PlanetEntity.builder()
                                .shortName(p.getOrigin())
                                .children(new HashSet<>())
                                .parents(new HashSet<>())
                                .build());

                // The child
                PlanetEntity destination = planetRepository
                        .findByShortName(p.getDestination())
                        .stream()
                        .findFirst()
                        .orElse(PlanetEntity.builder()
                                .shortName(p.getDestination())
                                .build());

                // Keep linked children
                source.getChildren().add(destination);

                // Persist parent first
                planetRepository.save(source);
            }
        });
    }

    public Planet getPlanet(String shortName) {
        return planetRepository.findByShortName(shortName)
                .stream()
                .findFirst()
                .map(entity -> Planet.builder()
                        .routeId(entity.getRouteId())
                        .shortName(entity.getShortName())
                        .children(entity.getChildren()
                                .stream()
                                .map(child -> getPlanet(child.getShortName()))
                                .collect(Collectors.toSet()))
                        .build())
                .orElse(new Planet());
    }

    public List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<>();
        planetRepository.findAll()
                .forEach(p -> planets.add(planetMapper.entityToDto(p)));
        return planets;
    }

    public PlanetEntity getPlanetEntity(long routeId) throws PlanetNotFound {
        Optional<PlanetEntity> planetOptional = planetRepository.findById(routeId);
        return planetOptional.orElseThrow(PlanetNotFound::new);
    }

    public PlanetDto getPlanet(long routeId) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(routeId);
        //return planetMapper.entityToDto(planetEntity);
        return new PlanetDto();
    }

    public boolean insertPlanet(PlanetDto planetDto) {
        PlanetEntity entity = planetMapper.dtoToEntity(planetDto);
        planetRepository.save(entity);
        return true;
    }

    public boolean insertPlanetBulk(List<PlanetDto> planets) {
        persistPlanets(planets);
        return true;
    }

    public boolean updatePlanet(PlanetDto planetDto) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(planetDto.getRouteId());
        //Todo
        //planetEntity.setOrigin(planetDto.getOrigin());
        //planetEntity.setDestination(planetDto.getDestination());
        planetRepository.save(planetEntity);
        return true;
    }

    public boolean deletePlanet(long routeId) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(routeId);
        planetRepository.delete(planetEntity);
        return true;
    }

    public void purgePlanets() {
        planetRepository.deleteAll();
    }

}
