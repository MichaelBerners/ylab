package in.controller;

import domain.dao.impl.CounterReadingsDaoImpl;
import domain.dao.impl.UserAuditDaoImpl;
import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.dto.response.CounterReadingResponse;
import lombok.Data;
import service.CounterReadingsService;
import service.impl.CounterReadingsServiceImpl;
import service.impl.UserAuditServiceImpl;
import util.ConnectionManager;

import java.util.List;


public class CountersReadingsController {

    private final CounterReadingsService counterReadingsService = new CounterReadingsServiceImpl(
            new UserAuditServiceImpl(new UserAuditDaoImpl(ConnectionManager.getConnection())),
            new CounterReadingsDaoImpl(ConnectionManager.getConnection())
    );

    private static final CountersReadingsController INSTANCE = new CountersReadingsController();

    private CountersReadingsController(){}
    public static CountersReadingsController getInstance() {
        return INSTANCE;
    }

    public CounterReadingResponse create(CounterReadingCreateRequest counterReadingCreateRequest) {

        return counterReadingsService.create(counterReadingCreateRequest);
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
