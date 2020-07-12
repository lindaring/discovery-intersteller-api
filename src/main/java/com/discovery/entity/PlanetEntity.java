package com.discovery.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class PlanetEntity {
    @javax.persistence.Id
    private Long routeId;

    private String origin;

    private String destination;
}
