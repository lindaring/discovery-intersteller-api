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
    private static final int TRAFFIC_COLUMN_INDEX = 2;

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
                    .build());
        }
        return planets;
    }

    private void getPlanetsDistance() {

    }

    public void importPlanets(MultipartFile file) throws IOException {
        List<PlanetImport> planets = getPlanetsTrafficFromFile(file);
        planetService.purgePlanets();
        planetService.persistPlanets(planets);
    }
}
