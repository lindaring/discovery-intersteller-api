package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "route_id", nullable = false, unique = true)
    private Long routeId;

    private String shortName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="planet_destination",
        joinColumns=@JoinColumn(name="origin_id"),
        inverseJoinColumns=@JoinColumn(name="destination_id"))
    private Set<PlanetEntity> parents = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="planet_destination",
        joinColumns=@JoinColumn(name="destination_id"),
        inverseJoinColumns=@JoinColumn(name="origin_id"))
    private Set<PlanetEntity> children = new HashSet<>();

    public Set<PlanetEntity> getChildren() {
        if (children == null) {
            children = new HashSet<>();
        }
        return children;
    }

    public Set<PlanetEntity> getParents() {
        if (parents == null) {
            parents = new HashSet<>();
        }
        return parents;
    }

    @Override
    public String toString() {
        return "PlanetEntity{" +
                "routeId=" + getRouteId() +
                ", shortName='" + getShortName() + '}';
    }
}
