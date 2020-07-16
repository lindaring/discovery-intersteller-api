package com.discovery.service;

import com.discovery.dto.Planet;
import com.discovery.dto.Route;
import com.discovery.dto.Routes;
import com.discovery.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;

    public Routes calculateShortest(SearchParams searchParams) {
        Planet planet = planetService.getPlanet(searchParams.getOrigin());

        Routes routes = new Routes();
        List<String> tempRoute = new ArrayList<>();
        tempRoute.add(searchParams.getOrigin());

        getRoutes(planet, searchParams, tempRoute, routes);
        return routes;
    }

    public void getRoutes(Planet planet, SearchParams params, List<String> temp, Routes routes) {
        // Loop through destinations
        for (Planet dest : planet.getChildren()) {
            List<String> temp2 = new ArrayList<>();
            temp2.add(planet.getShortName());
            temp2.add(dest.getShortName());
            //getRoutes(dest, params, new ArrayList<>(temp), routes);
            routes.getRouteList().add(Route.builder()
                    .route(new ArrayList<>(temp2)) // To avoid adding refs to final array
                    .build());

        }

        //if (params.getDestination().equals(temp.get(lastElementIndex))) { // Add route if it gets to the desired destination

        //}

        //temp.remove(temp.get(lastElementIndex)); // Remove the last element, we'll find another route from there.
    }

    public Set<Planet> getDestinations(List<Planet> planets, String origin) {
        return planets.stream()
                .filter(p -> p.getShortName().equals(origin))
                .map(Planet::getChildren)
                .findFirst()
                .orElse(new HashSet<>());
    }

}
