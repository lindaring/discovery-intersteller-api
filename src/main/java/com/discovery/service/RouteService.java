package com.discovery.service;

import com.discovery.dto.PlanetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteService {
    private final PlanetService planetService;

    public void calculateShortest() {
        List<PlanetDto> planets = planetService.getAllPlanets();

        String origin = "A";
        String destination = "Z";
        StringBuilder results = new StringBuilder("A");

        List<String> outerDestinations = getDestinations(planets, origin);

        for (String outerDestination : outerDestinations) {
            results.append(",").append(outerDestination);

            getInnerDestination(planets, outerDestination, results);
        }
    }

    public void getInnerDestination(List<PlanetDto> planets, String outerDestination, StringBuilder results) {
        List<String> innerDestinations = getDestinations(planets, outerDestination);
        for (String innerDestination : innerDestinations) {
            if (!outerDestination.equals(innerDestination)) {
                results.append(",").append(innerDestination);
                getInnerDestination(planets, innerDestination, results);
            }
        }
        log.info(results.toString());
        results.delete(results.length() - (outerDestination.length() + 1), results.length());
    }

    public List<String> getDestinations(List<PlanetDto> planets, String origin) {
        return planets.stream()
                .filter(x -> x.getOrigin().equals(origin))
                .map(PlanetDto::getDestination)
                .collect(Collectors.toList());
    }

}
