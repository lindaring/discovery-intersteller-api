package com.discovery.repository;

import com.discovery.entity.PlanetEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<PlanetEntity, Long> {
}
