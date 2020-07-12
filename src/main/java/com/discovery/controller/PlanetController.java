package com.discovery.controller;

import com.discovery.entity.PlanetEntity;
import com.discovery.mapper.PlanetMapper;
import com.discovery.model.PlanetModel;
import com.discovery.repository.PlanetRepository;
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
    private final PlanetMapper planetMapper;
    private final PlanetRepository planetRepository;

    @PostMapping("/import")
    public void importFile(@RequestParam("file") MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(2);

        List<PlanetEntity> newPlanetList = new ArrayList<>();

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            newPlanetList.add(PlanetEntity.builder()
                    .origin(row.getCell(1).getStringCellValue())
                    .destination(row.getCell(2).getStringCellValue())
                    .build());
        }

        planetRepository.saveAll(newPlanetList);
    }

    @GetMapping
    public List<PlanetModel> getAll() {
        List<PlanetModel> planetModelList = new ArrayList<>();
        planetRepository.findAll().forEach(x -> {
            planetModelList.add(planetMapper.entityToModel(x));
        });
        return planetModelList;
    }

    @PostMapping
    public void insertRecord() {
        PlanetEntity newPlanetEntity = new PlanetEntity();
        newPlanetEntity.setOrigin("A");
        newPlanetEntity.setDestination("B");
        planetRepository.save(newPlanetEntity);
    }
}
