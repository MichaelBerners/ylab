package service;

import domain.dto.request.CounterTypeRequest;
import domain.dto.response.CounterTypeResponse;

public interface CounterTypeService {

    /**
     * метод создания нового типа счетчика
     * @param newType тип счетчика
     */
    CounterTypeResponse create(CounterTypeRequest counterTypeRequest);
}
