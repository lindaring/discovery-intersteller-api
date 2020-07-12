package com.discovery.mapper;

import com.discovery.dto.PlanetDto;
import com.discovery.entity.PlanetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanetMapper {
    PlanetDto entityToDto(PlanetEntity planetEntity);
    PlanetEntity dtoToEntity(PlanetDto planetDto);
}
