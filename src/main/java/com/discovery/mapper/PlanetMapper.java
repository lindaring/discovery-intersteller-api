package com.discovery.mapper;

import com.discovery.dto.Planet;
import com.discovery.dto.PlanetDto;
import com.discovery.entity.PlanetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanetMapper {
    @Mapping(target = "parent", ignore = true)
    Planet entityToDto(PlanetEntity planetEntity);
    PlanetEntity dtoToEntity(PlanetDto planetDto);
    List<PlanetEntity> dtosToEntities(List<PlanetDto> planetDto);
}
