package com.discovery.controller;

import com.discovery.model.Planet;
import com.discovery.model.PlanetImport;
import com.discovery.model.PlanetResponse;
import com.discovery.exception.BusinessRuleException;
import com.discovery.exception.PlanetNotFound;
import com.discovery.exception.TechnicalException;
import com.discovery.service.PlanetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/interstellar/planets")
@RequiredArgsConstructor
@RestController
public class PlanetController {
    private final PlanetService planetService;

    @GetMapping("/{shortName}")
    @ApiOperation(
        value = "Get a planet's information using it's short name (node).",
        response = PlanetResponse.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok", response = PlanetResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BusinessRuleException.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TechnicalException.class)})
    public Planet getPlanet(@PathVariable("shortName") String shortName) throws PlanetNotFound {
        return planetService.getPlanet(shortName);
    }

    @PostMapping
    @ApiOperation(
        value = "Import planet names, source, destination, traffic delay and distance.",
        response = PlanetResponse.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok", response = PlanetResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TechnicalException.class)})
    public ResponseEntity<PlanetResponse> addPlanet(@RequestBody List<PlanetImport> planetList) {
        planetService.persistPlanets(planetList);
        return ResponseEntity.ok(PlanetResponse.builder()
                .message("Imported successfully")
                .success(true)
                .build());
    }

    @DeleteMapping
    @PostMapping
    @ApiOperation(
        value = "Delete all entries in the database.",
        response = PlanetResponse.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok", response = PlanetResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TechnicalException.class)})
    public ResponseEntity<PlanetResponse> removePlanet() {
        planetService.purgePlanets();
        return ResponseEntity.ok(PlanetResponse.builder()
                .message("Database Purged.")
                .success(true)
                .build());
    }
}
