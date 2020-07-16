package com.discovery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Planet {
    private Long routeId;
    private String shortName;
    private Planet parent;
    private Set<Planet> children = new HashSet<>();
}
