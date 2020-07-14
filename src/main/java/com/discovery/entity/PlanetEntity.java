package com.discovery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class PlanetEntity {
    @Id
    @GeneratedValue
    private Long routeId;

    private String shortName;

    @ManyToOne
    private PlanetEntity parent;

    @OneToMany(mappedBy="parent")
    private Set<PlanetEntity> children;
}
