package domain.dto.mapper;

import domain.dto.response.CounterReadingResponse;
import domain.entity.CounterReading;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CounterReadingMapper {

    CounterReadingMapper INSTANCE = Mappers.getMapper(CounterReadingMapper.class);
    CounterReadingResponse counterReadingToCounterReadingResponse(CounterReading counterReading);
}
