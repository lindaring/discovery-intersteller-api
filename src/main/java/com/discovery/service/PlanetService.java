package com.discovery.service;

import com.discovery.dto.Planet;
import com.discovery.dto.PlanetImport;
import com.discovery.entity.PlanetEntity;
import com.discovery.exception.PlanetNotFound;
import com.discovery.mapper.PlanetMapper;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetMapper planetMapper;

    public void persistPlanets(List<PlanetImport> planets) {
        /*planets.forEach(p -> {
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
                Set<PlanetEntity> parents = new HashSet<>();
                parents.add(source);
                PlanetDestinationEntity relationshipP = new PlanetDestinationEntity();
                relationshipP.setParents(parents);
                source.setParent(relationshipP);

                Set<PlanetEntity> children = new HashSet<>();
                children.add(destination);
                PlanetDestinationEntity relationshipC = new PlanetDestinationEntity();
                relationshipC.set(parents);
                source.setParent(relationshipC);

                PlanetDestinationEntity relationship = new PlanetDestinationEntity();
                relationship.setChildren(children);

                // Persist parent first
                planetRepository.save(source);
                planetRepository.save(destination);
                planetDestinationRepository.save(relationship);
            }
        });*/
    }

    public Planet getPlanet(String shortName) {
        /*planetDestinationRepository.findAll()
            .forEach(pd -> {
                log.info(pd.getParents() == null ? "yes" : "no");
            });*/
        return null;
        /*return planetRepository.findByShortName(shortName)
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
                .orElse(new Planet());*/
    }

    public List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<>();
//        planetRepository.findAll()
//                .forEach(p -> planets.add(planetMapper.entityToDto(p)));
        return planets;
    }

    public PlanetEntity getPlanetEntity(long routeId) throws PlanetNotFound {
//        Optional<PlanetEntity> planetOptional = planetRepository.findById(routeId);
        //return planetOptional.orElseThrow(PlanetNotFound::new);
        return null;
    }

    public PlanetImport getPlanet(long routeId) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(routeId);
        //return planetMapper.entityToDto(planetEntity);
        return new PlanetImport();
    }

    public boolean insertPlanet(PlanetImport PlanetImport) {
        PlanetEntity entity = planetMapper.dtoToEntity(PlanetImport);
        //planetRepository.save(entity);
        return true;
    }

    public boolean insertPlanetBulk(List<PlanetImport> planets) {
        persistPlanets(planets);
        return true;
    }

    public boolean updatePlanet(PlanetImport PlanetImport) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(PlanetImport.getRouteId());
        //Todo
        //planetEntity.setOrigin(PlanetImport.getOrigin());
        //planetEntity.setDestination(PlanetImport.getDestination());
        //planetRepository.save(planetEntity);
        return true;
    }

    public boolean deletePlanet(long routeId) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(routeId);
        //planetRepository.delete(planetEntity);
        return true;
    }

    public void purgePlanets() {
        //planetRepository.deleteAll();
    }

}
