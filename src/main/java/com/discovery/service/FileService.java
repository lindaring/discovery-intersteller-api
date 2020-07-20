package com.discovery.service;

import com.discovery.model.PlanetImport;
import com.discovery.exception.PlanetImportFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    private static final int NODE_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;

    private final PlanetService planetService;

    private List<PlanetImport> getPlanetsTrafficFromFile(MultipartFile file) throws PlanetImportFailed {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(DISTANCE_SHEET_INDEX);

            List<PlanetImport> planets = new ArrayList<>();

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                planets.add(PlanetImport.builder()
                        .routeId((long) row.getCell(ROUTE_ID_COLUMN_INDEX).getNumericCellValue())
                        .originShort(row.getCell(ORIGIN_COLUMN_INDEX).getStringCellValue())
                        .destinationShort(row.getCell(DESTINATION_COLUMN_INDEX).getStringCellValue())
                        .distance(row.getCell(DISTANCE_COLUMN_INDEX).getNumericCellValue())
                        .build());
            }
            determineTraffic(file, planets);
            determineFullNames(file, planets);
            return planets;
        } catch (IOException e) {
            log.error("Failed to import planet data.", e);
            throw new PlanetImportFailed("Failed to import planet data. File format may be incorrect");
        }
    }

    private void determineTraffic(MultipartFile file, List<PlanetImport> planets) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(TRAFFIC_SHEET_INDEX);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            String source = row.getCell(ORIGIN_COLUMN_INDEX).getStringCellValue();
            String destination = row.getCell(DESTINATION_COLUMN_INDEX).getStringCellValue();

            planets.stream()
                .filter(p -> source.equalsIgnoreCase(p.getOriginShort()) && destination.equalsIgnoreCase(p.getDestinationShort()))
                .findFirst()
                .ifPresent(p -> p.setTraffic(row.getCell(TRAFFIC_COLUMN_INDEX).getNumericCellValue()));
        }
    }

    private void determineFullNames(MultipartFile file, List<PlanetImport> planets) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(PLANET_NAME_SHEET_INDEX);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);

            String node = row.getCell(NODE_COLUMN_INDEX).getStringCellValue();
            String name = row.getCell(NAME_COLUMN_INDEX).getStringCellValue();

            planets.stream()
                .filter(p -> node.equalsIgnoreCase(p.getOriginShort()))
                .forEach(p -> p.setOriginFull(name));

            planets.stream()
                .filter(p -> node.equalsIgnoreCase(p.getDestinationShort()))
                .forEach(p -> p.setDestinationFull(name));
        }
    }

    public void importPlanets(MultipartFile file) throws PlanetImportFailed {
        List<PlanetImport> planets = getPlanetsTrafficFromFile(file);
        planetService.purgePlanets();
        planetService.persistPlanets(planets);
    }
}
