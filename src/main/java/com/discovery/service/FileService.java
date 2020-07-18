package com.discovery.service;

import com.discovery.dto.PlanetImport;
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
public class FileService {
    private static final int PLANET_NAME_SHEET_INDEX = 0;
    private static final int DISTANCE_SHEET_INDEX = 1;
    private static final int TRAFFIC_SHEET_INDEX = 2;

    private static final int ROUTE_ID_COLUMN_INDEX = 0;
    private static final int ORIGIN_COLUMN_INDEX = 1;
    private static final int DESTINATION_COLUMN_INDEX = 2;
    private static final int TRAFFIC_COLUMN_INDEX = 3;
    private static final int DISTANCE_COLUMN_INDEX = 3;

    private final PlanetService planetService;

    private List<PlanetImport> getPlanetsTrafficFromFile(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(DISTANCE_SHEET_INDEX);

        List<PlanetImport> planets = new ArrayList<>();

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            planets.add(PlanetImport.builder()
                    .routeId((long) row.getCell(ROUTE_ID_COLUMN_INDEX).getNumericCellValue())
                    .origin(row.getCell(ORIGIN_COLUMN_INDEX).getStringCellValue())
                    .destination(row.getCell(DESTINATION_COLUMN_INDEX).getStringCellValue())
                    .distance(row.getCell(DISTANCE_COLUMN_INDEX).getNumericCellValue())
                    .build());
        }
        determineTraffic(file, planets);
        return planets;
    }

    private void determineTraffic(MultipartFile file, List<PlanetImport> planets) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(TRAFFIC_SHEET_INDEX);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            String source = row.getCell(ORIGIN_COLUMN_INDEX).getStringCellValue();
            String destination = row.getCell(DESTINATION_COLUMN_INDEX).getStringCellValue();

            planets.stream()
                .filter(p -> source.equalsIgnoreCase(p.getOrigin()) && destination.equalsIgnoreCase(p.getDestination()))
                .findFirst()
                .ifPresent(p -> p.setTraffic(row.getCell(DISTANCE_COLUMN_INDEX).getNumericCellValue()));
        }
    }

    public void importPlanets(MultipartFile file) throws IOException {
        List<PlanetImport> planets = getPlanetsTrafficFromFile(file);
        planetService.purgePlanets();
        planetService.persistPlanets(planets);
    }
}
