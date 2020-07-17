package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "planet")
public class PlanetEntity {
    @Id
    @GeneratedValue
    private Long routeId;

    private String shortName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parent")
    private PlanetDestinationEntity parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_child")
    private PlanetDestinationEntity child;

    @Override
    public String toString() {
        return "PlanetEntity{" +
                "routeId=" + getRouteId() +
                ", shortName='" + getShortName() + '}';
    }
}
