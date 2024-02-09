package domain.dao;

import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.entity.CounterReading;

import java.util.List;

public interface CounterReadingsDao {

    void create(CounterReadingCreateRequest counterReadingCreateRequest);
    List<CounterReading> findActualCounterReadingsByUserId(Long userId);
    List<CounterReading> findCounterReadingsByUserIdAndYearMonth(CounterReadingYearMonthRequest counterReadingYearMonthRequest);
    List<CounterReading> findAllByUserId(Long userId);
}
