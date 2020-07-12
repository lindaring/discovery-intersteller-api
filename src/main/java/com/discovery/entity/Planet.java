package com.discovery.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Data
@Entity
public class Planet {
    @javax.persistence.Id
    @GeneratedValue
    private Long routeId;

    private String origin;

    private String destination;
}
