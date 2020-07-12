package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class PlanetEntity {
    @javax.persistence.Id
    @GeneratedValue
    private Long routeId;

    private String origin;

    private String destination;
}
