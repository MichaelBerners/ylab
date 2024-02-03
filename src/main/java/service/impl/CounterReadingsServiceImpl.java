package service.impl;

import domain.dao.CounterReadingsDao;
import domain.entity.CounterReadings;

import lombok.Data;
import service.CounterReadingsService;
import service.UserAuditService;


import java.util.List;

@Data
public class CounterReadingsServiceImpl implements CounterReadingsService {

    private final UserAuditService userAuditService;
    CounterReadingsDao counterReadingsDao;

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
    @Override
    public void create(Long userId, String counterType, Double readings) {
        counterReadingsDao.create(userId, counterType, readings);
        userAuditService.create(userId, "create counter readings " + counterType);
    }

    /**
     * Метод получения актуальных показаний счетчиков
     * @param userId лицевой счет пользователя
     * @return список актуальныхпоказаний счетчиков за последний месяц
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     */
    @Override
    public List<CounterReadings> readActualReadings(Long userId) {
        List<CounterReadings> result = counterReadingsDao.findActualCounterReadingsByUserId(userId);
        userAuditService.create(userId, "read actual counter readings");

        return result;
    }

    /**
     * Метод получения показаний счетчиков за определенный месяц
     * @param userId лицевой счет пользователя
     * @param month - месяц за который ищутся показания
     * @return список показаний пользователя за конкретный месяц
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("There are no counter readings for this month") в случае если показания счечиков
     * за текущий месяц отсутствуют
     */
    @Override
    public List<CounterReadings> readMonthReadings(Long userId, int month) {
        List<CounterReadings> result = counterReadingsDao.findCounterReadingsByUserIdAndMonth(userId, month);
        userAuditService.create(userId, "read the readings for the month : " + month);

        return result;
    }

    /**
     * Метод вывода итории подачи показаний сгруппированных по типу
     * @param userId лицевой счет пользователя
     * @return все показания пользователя сгруппированные по типу
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("There are no counter readings") в случае если показания счечиков отсутствуют
     */
    @Override
    public List<CounterReadings> readHistoryReadings(Long userId) {
        List<CounterReadings> result = counterReadingsDao.findAllByUserId(userId);
        userAuditService.create(userId, "read all the user's readings");

        return result;
    }

}
