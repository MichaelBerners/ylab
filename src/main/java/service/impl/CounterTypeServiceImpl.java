package service.impl;

import domain.dao.CounterTypeDao;
import lombok.Data;
import service.CounterTypeService;

@Data
public class CounterTypeServiceImpl implements CounterTypeService {
    private final CounterTypeDao counterTypeDao;
    @Override
    public void create(String newType) {
        counterTypeDao.create(newType);
    }
}
