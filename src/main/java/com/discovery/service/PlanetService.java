package com.discovery.service;

import com.discovery.entity.PlanetEntity;
import com.discovery.mapper.PlanetMapper;
import com.discovery.model.PlanetModel;
import com.discovery.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanetService {
    private final int TRAFFIC_SHEET_INDEX = 2;
    private final int ROUTE_ID_INDEX = 0;
    private final int ORIGIN_INDEX = 1;
    private final int DESTINATION_INDEX = 2;
    private final PlanetRepository planetRepository;
    private final PlanetMapper planetMapper;

    public List<PlanetEntity> getPlanets(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(TRAFFIC_SHEET_INDEX);

        List<PlanetEntity> planets = new ArrayList<>();

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            planets.add(PlanetEntity.builder()
                    .routeId((long) row.getCell(ROUTE_ID_INDEX).getNumericCellValue())
                    .origin(row.getCell(ORIGIN_INDEX).getStringCellValue())
                    .destination(row.getCell(DESTINATION_INDEX).getStringCellValue())
                    .build());
        }
        return planets;
    }

    public void persistPlanets(List<PlanetEntity> planets) {
        planetRepository.saveAll(planets);
    }

    public List<PlanetModel> getAllPlanets() {
        List<PlanetModel> planets = new ArrayList<>();
        planetRepository.findAll().forEach(x -> planets.add(planetMapper.entityToModel(x)));
        return planets;
    }

}
