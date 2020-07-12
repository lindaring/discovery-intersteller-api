package com.discovery.mapper;

import com.discovery.dto.PlanetDto;
import com.discovery.entity.PlanetEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanetMapper {
    PlanetDto entityToDto(PlanetEntity planetEntity);
    PlanetEntity dtoToEntity(PlanetDto planetDto);
    List<PlanetEntity> dtosToEntities(List<PlanetDto> planetDto);
}
