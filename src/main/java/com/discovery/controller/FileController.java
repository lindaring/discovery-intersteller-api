package com.discovery.controller;

import com.discovery.dto.PlanetImportResponse;
import com.discovery.exception.BusinessRuleException;
import com.discovery.exception.PlanetImportFailed;
import com.discovery.exception.TechnicalException;
import com.discovery.service.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/interstellar/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @PostMapping("/import")
    @ApiOperation(
        value = "Import planet names, source, destination, traffic delay and distance.",
        response = PlanetImportResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok", response = PlanetImportResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BusinessRuleException.class),
            @ApiResponse(code = 404, message = "Not Found", response = BusinessRuleException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = TechnicalException.class)})
    public ResponseEntity<PlanetImportResponse> importFile(@RequestParam("file") MultipartFile file) throws PlanetImportFailed {
        fileService.importPlanets(file);
        return ResponseEntity.ok(PlanetImportResponse.builder()
                .message("Imported successfully")
                .success(true)
                .build());
    }
}
