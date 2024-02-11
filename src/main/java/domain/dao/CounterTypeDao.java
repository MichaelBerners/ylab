package domain.dao;

import domain.entity.CounterType;

public interface CounterTypeDao {

    CounterType create(String newType);
}
