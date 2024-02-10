package in.controller;

import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.dto.response.CounterReadingResponse;
import lombok.Data;
import service.CounterReadingsService;

import java.util.List;

@Data
public class CountersReadingsController {

    private final CounterReadingsService counterReadingsService;

    public void create(CounterReadingCreateRequest counterReadingCreateRequest) {
        counterReadingsService.create(counterReadingCreateRequest);
    }

    public List<CounterReadingResponse> readActualReadings(Long userId) {

        return counterReadingsService.readActualReadings(userId);
    }

    public List<CounterReadingResponse> readYearMonthReadings(CounterReadingYearMonthRequest counterReadingYearMonthRequest) {

        return counterReadingsService.readYearMonthReadings(counterReadingYearMonthRequest);
    }

    public List<CounterReadingResponse> readHistoryReadings(Long userId) {

        return counterReadingsService.readHistoryReadings(userId);
    }


}
