package com.discovery.controller;

import com.discovery.dto.PlanetDto;
import com.discovery.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/interstellar")
@RequiredArgsConstructor
@RestController
public class PlanetController {
    private final PlanetService planetService;

    @PostMapping("/import")
    public void importFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<PlanetDto> planets = planetService.getPlanetsFromFile(file);
        planetService.persistPlanets(planets);
    }

    @GetMapping
    public List<PlanetDto> getAll() {
        return planetService.getAllPlanets();
    }
}
