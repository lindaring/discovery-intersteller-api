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
@Table(name = "planet_destination")
public class PlanetDestinationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private double distance; //todo populate distance

    @OneToMany(mappedBy = "parent")
    private Set<PlanetEntity> parents = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<PlanetEntity> children = new HashSet<>();

}
