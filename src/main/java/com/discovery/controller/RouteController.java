package com.discovery.controller;

import com.discovery.dto.PlanetResponse;
import com.discovery.dto.Routes;
import com.discovery.dto.SearchParams;
import com.discovery.exception.TechnicalException;
import com.discovery.service.RouteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/interstellar/routes")
@RequiredArgsConstructor
@RestController
public class RouteController {
    private final RouteService routeService;

    @GetMapping("/{origin}/{destination}")
    @ApiOperation(
        value = "Get the short route between the provide source and destination.",
        response = PlanetResponse.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok", response = PlanetResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TechnicalException.class)})
    public Routes getShortestRoutes(@PathVariable("origin") String origin,
                                    @PathVariable("destination") String destination) {
        return routeService.calculateShortest(SearchParams.builder()
                .origin(origin.toUpperCase())
                .destination(destination.toUpperCase())
                .build());
    }
}
