package domain.dto.mapper;

import domain.dto.response.CounterTypeResponse;
import domain.entity.CounterType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CounterTypeMapper {

    CounterTypeMapper INSTANCE = Mappers.getMapper(CounterTypeMapper.class);

    CounterTypeResponse counterTypeTocounterTypeResponse(CounterType counterType);
}
