package com.discovery.model;

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
    private Long planetId;
    private String shortName;
    private String fullName;
    private Planet parent;
    private double distanceFromParent;
    private double trafficFromParent;
    private Set<Planet> children = new HashSet<>();

    @Override
    public String toString() {
        return "Planet{" +
                "routeId=" + planetId +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
