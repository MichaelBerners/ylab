package in.controller;

import domain.dao.impl.CounterTypeDaoImpl;
import domain.dto.request.CounterTypeRequest;
import domain.dto.response.CounterTypeResponse;
import service.CounterTypeService;
import service.impl.CounterTypeServiceImpl;
import util.ConnectionManager;

public class CounterTypesController {

    private static final CounterTypesController INSTANCE = new CounterTypesController();

    private final CounterTypeService counterTypeService =
            new CounterTypeServiceImpl(new CounterTypeDaoImpl(ConnectionManager.getConnection()));

    private CounterTypesController(){}

    public static CounterTypesController getInstance() {

        return INSTANCE;
    }

    public CounterTypeResponse create(CounterTypeRequest counterTypeRequest) {

        return counterTypeService.create(counterTypeRequest);
    }
}
