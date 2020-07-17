package com.discovery.controller;

import com.discovery.dto.PlanetImport;
import com.discovery.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/interstellar/planets/bulk")
@RequiredArgsConstructor
@RestController
public class PlanetBulkController {
    private final PlanetService planetService;

    @PostMapping
    public boolean addPlanetBulk(@RequestBody List<PlanetImport> planets) {
        return planetService.insertPlanetBulk(planets);
    }
}
