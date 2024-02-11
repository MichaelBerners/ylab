package service.impl;

import domain.dao.CounterTypeDao;
import domain.dao.impl.CounterTypeDaoImpl;
import domain.dto.mapper.CounterTypeMapper;
import domain.dto.request.CounterTypeRequest;
import domain.dto.response.CounterTypeResponse;
import domain.entity.CounterType;
import lombok.Data;
import service.CounterTypeService;

@Data
public class CounterTypeServiceImpl implements CounterTypeService {
    private final CounterTypeDao counterTypeDao;
    private final CounterTypeMapper counterTypeMapper = CounterTypeMapper.INSTANCE;
    /**
     * метод создания нового типа счетчика
     *
     */
    @Override
    public CounterTypeResponse create(CounterTypeRequest counterTypeRequest) {
        CounterType counterType = counterTypeDao.create(counterTypeRequest.getNewType());

        return counterTypeMapper.counterTypeTocounterTypeResponse(counterType);
    }
}
