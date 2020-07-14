package com.discovery.controller;

import com.discovery.dto.Routes;
import com.discovery.service.RouteService;
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
    public Routes getShortestRoutes(@PathVariable("origin") String origin,
                                    @PathVariable("destination") String destination) {
        return routeService.calculateShortest(origin, destination);
    }
}
