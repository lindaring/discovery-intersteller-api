package com.discovery.mapper;

import com.discovery.model.PlanetModel;
import com.discovery.entity.PlanetEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PlanetMapper {
    PlanetModel entityToModel(PlanetEntity planetEntity);
    PlanetEntity modelToEntity(PlanetModel planetModel);
}
