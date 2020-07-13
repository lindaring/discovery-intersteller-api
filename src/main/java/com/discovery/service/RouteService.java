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

    public void calculateShortest(String origin, String destination) {
        List<PlanetDto> planets = planetService.getAllPlanets();

        StringBuilder results = new StringBuilder();
        results.append(origin);

        getInnerDestination(planets, origin, destination, results);
    }

    public void getInnerDestination(List<PlanetDto> planets, String origin, String end, StringBuilder results) {
        List<String> innerDestinations = getDestinations(planets, origin);

        for (String innerDestination : innerDestinations) {
            if (origin.equals(innerDestination)) {
                break;
            }
            results.append(",").append(innerDestination);
            if (end.equals(innerDestination)) {
                break;
            }
            getInnerDestination(planets, innerDestination, end, results);
        }

        if (end.equals(results.substring(results.length() - origin.length(), results.length()))) {
            log.info(results.toString());
        }

        results.delete(results.length() - (origin.length() + 1), results.length());
    }

    public List<String> getDestinations(List<PlanetDto> planets, String origin) {
        return planets.stream()
                .filter(x -> x.getOrigin().equals(origin))
                .map(PlanetDto::getDestination)
                .collect(Collectors.toList());
    }

}
