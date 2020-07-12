package com.discovery.service;

import com.discovery.dto.PlanetDto;
import com.discovery.entity.PlanetEntity;
import com.discovery.exception.PlanetNotFound;
import com.discovery.mapper.PlanetMapper;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetMapper planetMapper;

    public void persistPlanets(List<PlanetDto> planets) {
        List<PlanetEntity> entities = new ArrayList<>();
        planets.forEach(p -> entities.add(planetMapper.dtoToEntity(p)));
        planetRepository.saveAll(entities);
    }

    public List<PlanetDto> getAllPlanets() {
        List<PlanetDto> planets = new ArrayList<>();
        planetRepository.findAll().forEach(x -> planets.add(planetMapper.entityToDto(x)));
        return planets;
    }

    public PlanetEntity getPlanetEntity(long routeId) throws PlanetNotFound {
        Optional<PlanetEntity> planetOptional = planetRepository.findById(routeId);
        return planetOptional.orElseThrow(PlanetNotFound::new);
    }

    public PlanetDto getPlanet(long routeId) throws PlanetNotFound {
        PlanetEntity planetEntity = getPlanetEntity(routeId);
        return planetMapper.entityToDto(planetEntity);
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
        planetEntity.setOrigin(planetDto.getOrigin());
        planetEntity.setDestination(planetDto.getDestination());
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
