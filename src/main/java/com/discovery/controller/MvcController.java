package com.discovery.controller;

import com.discovery.exception.PlanetImportFailed;
import com.discovery.model.Routes;
import com.discovery.model.SearchParams;
import com.discovery.service.FileService;
import com.discovery.service.PlanetService;
import com.discovery.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MvcController {
    private final PlanetService planetService;
    private final RouteService routeService;
    private final FileService fileService;
    private Map<String, String> planetNames;

    @GetMapping("/")
    public String indexPage(Model model) {
        // Populate dropdowns
        planetNames = planetService.getPlanetNames();
        if (planetNames == null || planetNames.isEmpty()) {
            return "upload";
        }
        model.addAttribute("planets", planetNames);
        return "index";
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/result")
    public String resultPage(Model model, HttpServletRequest request) {
        if (planetNames == null || planetNames.isEmpty()) {
            return "upload";
        }
        String fromNode = request.getParameter("fromNode");
        String toNode = request.getParameter("toNode");

        if (fromNode.equals(toNode)) {
            model.addAttribute("errorMessage", "From planet must not be the same as to planet");
            model.addAttribute("planets", planetNames);
            return "index";
        }

        Routes routes = routeService.calculateShortest(SearchParams.builder()
                .origin(fromNode)
                .destination(toNode)
                .build());
        model.addAttribute("routes", routes.getRouteList());
        return "result";
    }

    @PostMapping("/import")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) throws PlanetImportFailed {
        // Validate file type
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            model.addAttribute("errorMessage", "File type not supported. Select a (.xls) or (.xlsx) file.");
            return "upload";
        }

        // Import from excel file
        fileService.importPlanets(file);

        // Populate dropdowns
        planetNames = planetService.getPlanetNames();
        if (planetNames == null || planetNames.isEmpty()) {
            return "upload";
        }
        model.addAttribute("planets", planetNames);
        return "index";
    }

}
