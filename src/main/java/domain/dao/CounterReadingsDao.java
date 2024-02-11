package domain.dao;

import domain.dto.request.CounterReadingYearMonthRequest;
import domain.entity.CounterReading;

import java.util.List;

public interface CounterReadingsDao {

    CounterReading create(Long userId, String counterType, Double readings);
    List<CounterReading> findActualCounterReadingsByUserId(Long userId);
    List<CounterReading> findCounterReadingsByUserIdAndYearMonth(Long userId, int year, int month);
    List<CounterReading> findAllByUserId(Long userId);
}
