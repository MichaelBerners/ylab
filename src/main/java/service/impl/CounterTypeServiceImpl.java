package service.impl;

import domain.dao.CounterTypeDao;
import domain.dao.impl.CounterTypeDaoImpl;
import lombok.Data;
import service.CounterTypeService;

@Data
public class CounterTypeServiceImpl implements CounterTypeService {
    private final CounterTypeDao counterTypeDao;
    /**
     * метод создания нового типа счетчика
     * @param newType тип счетчика
     */
    @Override
    public void create(String newType) {
        counterTypeDao.create(newType);
    }
}
