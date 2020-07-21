package com.discovery.model;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.Map;

@Data
@Builder
public class Route {
    private Map<String, String> route;
    private double distance;
    private double traffic;
    private boolean isShortest;
    private boolean isQuickest;
    private boolean highlight;
    public String getRoutePath() {
        StringBuilder path = new StringBuilder();
        for (Iterator<String> iterator = route.keySet().iterator(); iterator.hasNext(); ) {
            String node = iterator.next();
            path.append(node);
            if (iterator.hasNext()) {
                path.append(" => ");
            }
        }
        return path.toString();
    }
}
