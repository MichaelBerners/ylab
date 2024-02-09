package domain.dao;

import domain.entity.CounterReadings;

import java.util.List;

public interface CounterReadingsDao {

    void create(Long userId, String counterType, Double readings);
    List<CounterReadings> findActualCounterReadingsByUserId(Long userId);
    List<CounterReadings> findCounterReadingsByUserIdAndYearMonth(Long userId, int year, int month);
    List<CounterReadings> findAllByUserId(Long userId);
}
