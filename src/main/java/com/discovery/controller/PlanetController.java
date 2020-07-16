package com.discovery.controller;

import com.discovery.dto.Planet;
import com.discovery.dto.PlanetDto;
import com.discovery.exception.PlanetNotFound;
import com.discovery.service.FileService;
import com.discovery.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/interstellar/planets")
@RequiredArgsConstructor
@RestController
public class PlanetController {
    private final PlanetService planetService;

    @GetMapping
    public List<Planet> getAllPlanets() {
        return planetService.getAllPlanets();
    }

    @GetMapping("/{id}")
    public PlanetDto getPlanet(@PathVariable("id") long id) throws PlanetNotFound {
        return planetService.getPlanet(id);
    }

    @PostMapping
    public boolean addPlanet(@RequestBody PlanetDto planetDto) {
        return planetService.insertPlanet(planetDto);
    }

    @PutMapping
    public boolean updatePlanet(@RequestBody PlanetDto planetDto) throws PlanetNotFound {
        return planetService.updatePlanet(planetDto);
    }

    @DeleteMapping("/{id}")
    public boolean removePlanet(@PathVariable("id") long id) throws PlanetNotFound {
        return planetService.deletePlanet(id);
    }
}
