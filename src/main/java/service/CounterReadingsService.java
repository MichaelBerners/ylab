package service;

import domain.entity.CounterReadings;

import java.util.List;
import java.util.Map;

public interface CounterReadingsService {

    /**
     * Метод создания нового показания счетчика
     * @param userId лицевой счет пользователя
     * @param counterType тип счетчика
     * @param readings показания счетчика
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("This is counter type not found") - тип счетчика не найден
     * @throw CounterReadingsException("The testimony can be submitted once a month!") в случае если у этого показания
     * совпадает: тип счетчика, месяц и serId с имеющимся в БД (нужно проверить т.к работает некорректно,
     * хотя в классе CounterReadings переопределины методы equals and hashcode на указанных полях)
     */
    void create(Long userId, String counterType, Double readings);

    /**
     * Метод получения актуальных показаний счетчиков
     * @param userId лицевой счет пользователя
     * @return список актуальныхпоказаний счетчиков за последний месяц
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     */
    List<CounterReadings> readActualReadings(Long userId);

    /**
     * Метод получения показаний счетчиков за определенный месяц
     * @param userId лицевой счет пользователя
     * @param month - месяц за который ищутся показания
     * @return список показаний пользователя за конкретный месяц
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("There are no counter readings for this month") в случае если показания счечиков
     * за текущий месяц отсутствуют
     */
    List<CounterReadings> readYearMonthReadings(Long userId, int year, int month);

    /**
     * Метод вывода итории подачи показаний сгруппированных по типу
     * @param userId лицевой счет пользователя
     * @return все показания пользователя сгруппированные по типу
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("There are no counter readings") в случае если показания счечиков отсутствуют
     */
    List<CounterReadings> readHistoryReadings(Long userId);
}
