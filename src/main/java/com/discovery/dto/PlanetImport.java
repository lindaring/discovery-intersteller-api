package com.discovery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanetImport {
    private Long routeId;
    private String originShort;
    private String originFull;
    private String destinationShort;
    private String destinationFull;
    private double distance;
    private double traffic;
}
