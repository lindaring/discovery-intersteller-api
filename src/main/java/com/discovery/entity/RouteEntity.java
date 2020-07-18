package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "route")
public class RouteEntity {
    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long routeId;

    private double distance;

    private double traffic;

    @ManyToOne
    @JoinColumn(referencedColumnName = "planet_id")
    private PlanetEntity source;

    @ManyToOne
    @JoinColumn(referencedColumnName = "planet_id")
    private PlanetEntity destination;

    @Override
    public String toString() {
        return "RouteEntity{" +
                "routeId=" + routeId +
                ", distance=" + distance +
                ", traffic=" + traffic +
                '}';
    }

}
