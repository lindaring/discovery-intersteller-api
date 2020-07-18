package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @Column(name = "planet_id")
    private Long planetId;

    private String shortName;

    private String fullName;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private Set<RouteEntity> destinations = new HashSet<>();

    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY)
    private Set<RouteEntity> sources = new HashSet<>();

    @Override
    public String toString() {
        return "PlanetEntity{" +
                "planetId=" + planetId +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
