package com.discovery.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Route {
    private Map<String, String> route;
    private double distance;
    private double traffic;
    private boolean isShortest;
    private boolean isQuickest;
}
