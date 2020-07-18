package com.discovery.service;

import com.discovery.dto.Planet;
import com.discovery.dto.Route;
import com.discovery.dto.Routes;
import com.discovery.dto.SearchParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;

    public Routes calculateShortest(SearchParams searchParams) {
        Planet planet = planetService.getPlanetWithDestinations(searchParams.getOrigin());

        Routes routes = new Routes();
        Map<String, Double> tempRoute = new LinkedHashMap<>();
        tempRoute.put(planet.getShortName(), planet.getDistanceFromParent());

        determinePossibleRoutes(planet, searchParams, tempRoute, routes);
        identifyShortestRoute(routes);

        return routes;
    }

    public void determinePossibleRoutes(Planet planet, SearchParams params, Map<String, Double> temp, Routes routes) {
        for (Planet dest : planet.getChildren()) {  // Loop through destinations
            temp.put(dest.getShortName(), dest.getDistanceFromParent());
            if (isDesiredDestination(dest.getShortName(), params.getDestination())) {
                recordPossibleRoute(temp, routes);
            } else if (!isEndReached(dest)) {
                determinePossibleRoutes(dest, params, temp, routes);
            }
            temp.remove(dest.getShortName());
        }
    }

    private boolean isDesiredDestination(String currentPosition, String endDestination) {
        return endDestination.equals(currentPosition);
    }

    private boolean isEndReached(Planet currentPosition) {
        return currentPosition.getChildren() == null
                || currentPosition.getChildren().isEmpty();
    }

    private void recordPossibleRoute(Map<String, Double> temp, Routes routes) { // Add route if it gets to the desired destination
        routes.getRouteList()
                .add(Route.builder()
                        .route(new LinkedHashSet<>(temp.keySet()))
                        .distance(temp.values().stream().mapToDouble(d -> d).sum())
                        .build());
    }

    private void identifyShortestRoute(Routes routes) {
        routes.getRouteList()
                .stream()
                .min(Comparator.comparingDouble(Route::getDistance))
                .ifPresent(x -> x.setShortest(true));
    }
}
