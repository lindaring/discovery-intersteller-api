package com.discovery.repository;

import com.discovery.entity.RouteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
    Set<RouteEntity> findBySource_ShortName(String shortName);
}
