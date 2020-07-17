package com.discovery.repository;

import com.discovery.entity.PlanetEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PlanetRepository {
    //public interface PlanetRepository extends CrudRepository<PlanetEntity, Long> {
    Set<PlanetEntity> findByShortName(String shortName);
}
