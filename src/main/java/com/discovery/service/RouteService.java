package com.discovery.service;

import com.discovery.dto.Planet;
import com.discovery.dto.Route;
import com.discovery.dto.Routes;
import com.discovery.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;

    public Routes calculateShortest(SearchParams searchParams) {
        Planet planet = planetService.getPlanet(searchParams.getOrigin());

        Routes routes = new Routes();
        Set<String> tempRoute = new LinkedHashSet<>();
        tempRoute.add(searchParams.getOrigin());

        getRoutes(planet, searchParams, tempRoute, routes);
        return routes;
    }

    public void getRoutes(Planet planet, SearchParams params, Set<String> temp, Routes routes) {
        for (Planet dest : planet.getChildren()) {  // Loop through destinations
            temp.add(dest.getShortName());
            if (isEndReached(dest.getShortName(), params.getDestination())) {
                routes.getRouteList().add(Route.builder().route(temp).build()); // Add route if it gets to the desired destination
            } else {
                getRoutes(dest, params, temp, routes);
            }
            temp.remove(dest.getShortName());
        }
    }

    private boolean isEndReached(String currentPosition, String endDestination) {
        return endDestination.equals(currentPosition);
    }

}
