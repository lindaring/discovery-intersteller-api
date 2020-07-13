package com.discovery.controller;

import com.discovery.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/interstellar/routes")
@RequiredArgsConstructor
@RestController
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public String getShortestRoutes() {
        routeService.calculateShortest();
        return "done";
    }
}
