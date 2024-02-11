package service.impl;

import domain.dao.CounterReadingsDao;
import domain.dto.mapper.CounterReadingMapper;
import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.dto.response.CounterReadingResponse;
import domain.entity.CounterReading;

import lombok.Data;
import service.CounterReadingsService;
import service.UserAuditService;


import java.util.List;
import java.util.stream.Collectors;

@Data
public class CounterReadingsServiceImpl implements CounterReadingsService {

    private final UserAuditService userAuditService;
    private final CounterReadingsDao counterReadingsDao;
    private final CounterReadingMapper counterReadingMapper = CounterReadingMapper.INSTANCE;

    /**
     * Метод создания нового показания счетчика
     *
     * @param counterReadingCreateRequest@throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" This is counter type not found ") - тип счетчика не найден
     * @throw CounterReadingsException(" The testimony can be submitted once a month ! ") в случае если у этого показания
     * совпадает: тип счетчика, месяц и serId с имеющимся в БД
     */
    @Override
    public CounterReadingResponse create(CounterReadingCreateRequest counterReadingCreateRequest) {
        Long userId = counterReadingCreateRequest.getUserId();
        String counterType = counterReadingCreateRequest.getCounterType();
        Double readings = counterReadingCreateRequest.getReadings();
        CounterReading counterReading = counterReadingsDao.create(userId, counterType, readings);
        userAuditService.create(counterReadingCreateRequest.getUserId(), "create counter readings " + counterReadingCreateRequest.getCounterType());

        return counterReadingMapper.counterReadingToCounterReadingResponse(counterReading);
    }

    /**
     * Метод получения актуальных показаний счетчиков
     *
     * @param userId лицевой счет пользователя
     * @return список актуальных показаний счетчиков за последний месяц
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     */
    @Override
    public List<CounterReadingResponse> readActualReadings(Long userId) {
        List<CounterReadingResponse> result = counterReadingsDao.findActualCounterReadingsByUserId(userId).stream()
                .map(counterReadingMapper::counterReadingToCounterReadingResponse).collect(Collectors.toList());
        userAuditService.create(userId, "read actual counter readings");

        return result;
    }

    /**
     * Метод получения показаний счетчиков за определенный месяц
     *
     * @param counterReadingYearMonthRequest@return список показаний пользователя за конкретный месяц
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" There are no counter readings for this month ") в случае если показания счечиков
     * за текущий месяц отсутствуют
     */
    @Override
    public List<CounterReadingResponse> readYearMonthReadings(CounterReadingYearMonthRequest counterReadingYearMonthRequest) {
        Long userId = counterReadingYearMonthRequest.getUserId();
        int year = counterReadingYearMonthRequest.getYear();
        int month = counterReadingYearMonthRequest.getMonth();
        List<CounterReading> counterReadings = counterReadingsDao.findCounterReadingsByUserIdAndYearMonth(userId, year, month);
        List<CounterReadingResponse> result = counterReadings.stream()
                .map(counterReadingMapper::counterReadingToCounterReadingResponse).collect(Collectors.toList());
        userAuditService.create(counterReadingYearMonthRequest.getUserId(),
                "read the readings for the year and month : " + counterReadingYearMonthRequest.getYear() + " , " +
                        counterReadingYearMonthRequest.getMonth());

        return result;
    }

    /**
     * Метод вывода итории подачи показаний сгруппированных по типу
     *
     * @param userId лицевой счет пользователя
     * @return все показания пользователя
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" There are no counter readings ") в случае если показания счечиков отсутствуют
     */
    @Override
    public List<CounterReadingResponse> readHistoryReadings(Long userId) {
        List<CounterReadingResponse> result = counterReadingsDao.findAllByUserId(userId).stream()
                .map(counterReadingMapper::counterReadingToCounterReadingResponse).collect(Collectors.toList());
        userAuditService.create(userId, "read all the user's readings");

        return result;
    }

}
