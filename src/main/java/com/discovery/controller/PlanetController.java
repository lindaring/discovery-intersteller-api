package com.discovery.controller;

import com.discovery.entity.PlanetEntity;
import com.discovery.mapper.PlanetMapper;
import com.discovery.model.PlanetModel;
import com.discovery.repository.PlanetRepository;
import com.discovery.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/interstellar")
@RequiredArgsConstructor
@RestController
public class PlanetController {
    private final PlanetService planetService;

    @PostMapping("/import")
    public void importFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<PlanetEntity> planets = planetService.getPlanets(file);
        planetService.persistPlanets(planets);
    }

    @GetMapping
    public List<PlanetModel> getAll() {
        return planetService.getAllPlanets();
    }
}
