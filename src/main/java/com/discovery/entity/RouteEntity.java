package com.discovery.entity;

import com.discovery.dto.Planet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

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
    @MapsId("planet_id")
    private PlanetEntity source;

    @ManyToOne
    @MapsId("sourceId")
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
