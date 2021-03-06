package com.discovery.service;

import com.discovery.model.Planet;
import com.discovery.model.Route;
import com.discovery.model.Routes;
import com.discovery.model.SearchParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;
    private Map<String, String> planetNames;

    public Routes calculateShortest(SearchParams searchParams) {
        Planet planet = planetService.getPlanetWithDestinations(searchParams.getOrigin());
        planetNames = planetService.getPlanetNames();

        Routes routes = new Routes();
        Map<String, Pair<Double, Double>> tempRoute = new LinkedHashMap<>();
        tempRoute.put(planet.getShortName(), Pair.of(planet.getDistanceFromParent(), planet.getTrafficFromParent()));

        determinePossibleRoutes(planet, searchParams, tempRoute, routes);
        identifyShortestRoute(routes);
        identifyFastestRoute(routes);

        return routes;
    }

    public void determinePossibleRoutes(Planet planet,
                                        SearchParams params,
                                        Map<String, Pair<Double, Double>> temp,
                                        Routes routes) {
        for (Planet dest : planet.getChildren()) {  // Loop through destinations
            temp.put(dest.getShortName(), Pair.of(dest.getDistanceFromParent(), dest.getTrafficFromParent()));
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

    private void recordPossibleRoute(Map<String, Pair<Double, Double>> temp, Routes routes) { // Add route if it gets to the desired destination
        routes.getRouteList()
                .add(Route.builder()
                        .route(cloneTemp(temp.keySet()))
                        .distance(temp.values().stream().mapToDouble(Pair::getFirst).sum())
                        .traffic(temp.values().stream().mapToDouble(Pair::getSecond).sum())
                        .build());
    }

    private Map<String, String> cloneTemp(Set<String> temp) {
        Map<String, String> nodeNames = new LinkedHashMap<>();
        temp.forEach(t -> nodeNames.put(t, planetNames.get(t)));
        return nodeNames;
    }

    private void identifyShortestRoute(Routes routes) {
        routes.getRouteList()
                .stream()
                .min(Comparator.comparingDouble(Route::getDistance))
                .ifPresent(route -> route.setShortest(true));
    }

    private void identifyFastestRoute(Routes routes) {
        final double SPEED = 7.5;
        routes.getRouteList()
                .stream()
                .min(Comparator.comparingDouble(route -> (route.getDistance() / SPEED) * route.getTraffic()))
                .ifPresent(route -> route.setQuickest(true));
    }
}
