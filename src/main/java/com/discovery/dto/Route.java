package com.discovery.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Route {
    private Map<String, String> route;
    private double distance;
    private double traffic;
    private boolean isShortest;
    private boolean isQuickest;
}
