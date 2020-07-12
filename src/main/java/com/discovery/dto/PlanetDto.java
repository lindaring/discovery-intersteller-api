package com.discovery.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlanetDto {
    private Long routeId;
    private String origin;
    private String destination;
}
