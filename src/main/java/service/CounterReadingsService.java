package service;

import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.dto.response.CounterReadingResponse;

import java.util.List;

public interface CounterReadingsService {

    /**
     * Метод создания нового показания счетчика
     *
     * @param counterReadingCreateRequest@throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" This is counter type not found ") - тип счетчика не найден
     * @throw CounterReadingsException(" The testimony can be submitted once a month ! ") в случае если у этого показания
     * совпадает: тип счетчика, месяц и serId с имеющимся в БД
     */
    void create(CounterReadingCreateRequest counterReadingCreateRequest);

    /**
     * Метод получения актуальных показаний счетчиков
     *
     * @param userId лицевой счет пользователя
     * @return список актуальных показаний счетчиков за последний месяц
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     */
    List<CounterReadingResponse> readActualReadings(Long userId);

    /**
     * Метод получения показаний счетчиков за определенный месяц
     *
     * @param counterReadingYearMonthRequest@return список показаний пользователя за конкретный месяц
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" There are no counter readings for this month ") в случае если показания счечиков
     * за текущий месяц отсутствуют
     */
    List<CounterReadingResponse> readYearMonthReadings(CounterReadingYearMonthRequest counterReadingYearMonthRequest);

    /**
     * Метод вывода итории подачи показаний сгруппированных по типу
     *
     * @param userId лицевой счет пользователя
     * @return все показания пользователя
     * @throw UserException(" User not found ") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException(" There are no counter readings ") в случае если показания счечиков отсутствуют
     */
    List<CounterReadingResponse> readHistoryReadings(Long userId);
}
