package com.discovery.service;

import com.discovery.model.Planet;
import com.discovery.model.PlanetImport;
import com.discovery.entity.PlanetEntity;
import com.discovery.entity.RouteEntity;
import com.discovery.exception.PlanetNotFound;
import com.discovery.repository.PlanetRepository;
import com.discovery.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final RouteRepository routeRepository;

    public void persistPlanets(List<PlanetImport> planets) {
        planets.forEach(p -> {
            log.info("Persisting {} {} ...", p.getOriginShort(), p.getDestinationShort());

            // Ensure uppercase
            p.setOriginShort(p.getOriginShort().toUpperCase());
            p.setDestinationShort(p.getDestinationShort().toUpperCase());

            if (!p.getOriginShort().equals(p.getDestinationShort())) { // Avoid storing broken data
                //The parent
                PlanetEntity sourcePlanet = planetRepository
                        .findByShortName(p.getOriginShort())
                        .stream()
                        .findFirst()
                        .orElse(PlanetEntity.builder()
                                .shortName(p.getOriginShort())
                                .fullName(p.getOriginFull())
                                .build());

                // The child
                PlanetEntity destinationPlant = planetRepository
                        .findByShortName(p.getDestinationShort())
                        .stream()
                        .findFirst()
                        .orElse(PlanetEntity.builder()
                                .shortName(p.getDestinationShort())
                                .fullName(p.getDestinationFull())
                                .build());

                RouteEntity route = RouteEntity.builder()
                        .source(sourcePlanet)
                        .destination(destinationPlant)
                        .distance(p.getDistance())
                        .traffic(p.getTraffic())
                        .build();

                sourcePlanet.setDestinations(getSourceDestination(route));

                planetRepository.save(sourcePlanet);
                planetRepository.save(destinationPlant);
                routeRepository.save(route);
            }
        });
    }

    private Set<RouteEntity> getSourceDestination(RouteEntity route) {
        Set<RouteEntity> routeList = new HashSet<>();
        routeList.add(route);
        return routeList;
    }

    public Planet getPlanetWithDestinations(String shortName) {
        Set<RouteEntity> routes = routeRepository.findBySource_ShortName(shortName);
        Planet planet = new Planet();
        planet.setShortName(shortName);
        setDestinations(routes, planet);
        return planet;
    }

    public void setDestinations(Set<RouteEntity> routes, Planet planet) {
        Set<Planet> children = new HashSet<>();
        for (RouteEntity routeEntity : routes) {
            Planet child = Planet.builder()
                    .planetId(routeEntity.getDestination().getPlanetId())
                    .shortName(routeEntity.getDestination().getShortName())
                    .fullName(routeEntity.getDestination().getFullName())
                    .distanceFromParent(routeEntity.getDistance())
                    .trafficFromParent(routeEntity.getTraffic())
                    .parent(planet)
                    .build();
            children.add(child);
            if (!routeEntity.getDestination().getSources().isEmpty()) {
                setDestinations(routeEntity.getDestination().getSources(), child);
            }
        }
        planet.setChildren(children);
    }

    public Planet getPlanet(String planetShortName) throws PlanetNotFound {
        Planet planet = getPlanetWithDestinations(planetShortName);
        if (planet == null) {
            throw new PlanetNotFound();
        }
        return planet;
    }

    public void purgePlanets() {
        routeRepository.deleteAll();
        planetRepository.deleteAll();
    }

    public Map<String, String> getPlanetNames() {
        Map<String, String> names = new HashMap<>();
        planetRepository.findAll()
            .forEach(p -> names.put(p.getShortName(), p.getFullName()));
        return names;
    }
}
