package com.discovery.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Routes {
    private long id;
    private Set<Route> routeList;
    public Set<Route> getRouteList() {
        if (routeList == null) {
            routeList = new HashSet<>();
        }
        return routeList;
    }
}
