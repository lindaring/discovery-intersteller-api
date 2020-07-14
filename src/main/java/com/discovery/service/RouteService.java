package com.discovery.service;

import com.discovery.dto.PlanetDto;
import com.discovery.dto.Route;
import com.discovery.dto.Routes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;

    public Routes calculateShortest(String origin, String destination) {
        List<PlanetDto> planets = planetService.getAllPlanets();

        Routes routes = new Routes();
        List<String> tempRoute = new ArrayList<>();
        tempRoute.add(origin);

        getRoutes(planets, origin, destination, tempRoute, routes);
        return routes;
    }

    public void getRoutes(List<PlanetDto> planets, String origin, String end, List<String> tempRoute, Routes routes) {
        List<String> destinations = getDestinations(planets, origin);

        for (String dest : destinations) {
            if (origin.equals(dest)) {
                break;
            }
            tempRoute.add(dest);
            if (end.equals(dest)) {
                break;
            }
            getRoutes(planets, dest, end, tempRoute, routes);
        }

        if (end.equals(tempRoute.get(tempRoute.size() - 1))) {
            Route routeCopy = Route.builder()
                    .route(new ArrayList<>(tempRoute)) // To avoid adding refs to final array
                    .build();
            routes.getRouteList().add(routeCopy);
        }

        tempRoute.remove(tempRoute.get(tempRoute.size() - 1));
    }

    public List<String> getDestinations(List<PlanetDto> planets, String origin) {
        return planets.stream()
                .filter(x -> x.getOrigin().equals(origin))
                .map(PlanetDto::getDestination)
                .collect(Collectors.toList());
    }

}
