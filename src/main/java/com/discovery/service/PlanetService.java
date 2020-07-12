package com.discovery.service;

import com.discovery.entity.PlanetEntity;
import com.discovery.mapper.PlanetMapper;
import com.discovery.dto.PlanetDto;
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

    public List<PlanetDto> getPlanetsFromFile(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(TRAFFIC_SHEET_INDEX);

        List<PlanetDto> planets = new ArrayList<>();

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            planets.add(PlanetDto.builder()
                    .routeId((long) row.getCell(ROUTE_ID_INDEX).getNumericCellValue())
                    .origin(row.getCell(ORIGIN_INDEX).getStringCellValue())
                    .destination(row.getCell(DESTINATION_INDEX).getStringCellValue())
                    .build());
        }
        return planets;
    }

    public void persistPlanets(List<PlanetDto> planets) {
        List<PlanetEntity> entities = new ArrayList<>();
        planets.forEach(p -> entities.add(planetMapper.dtoToEntity(p)));
        planetRepository.saveAll(entities);
    }

    public List<PlanetDto> getAllPlanets() {
        List<PlanetDto> planets = new ArrayList<>();
        planetRepository.findAll().forEach(x -> planets.add(planetMapper.entityToDto(x)));
        return planets;
    }

}
