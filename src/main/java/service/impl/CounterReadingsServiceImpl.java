package service.impl;

import domain.entity.CounterReadings;
import domain.entity.CounterTypeMap;
import domain.entity.User;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import lombok.Data;
import service.CounterReadingsService;
import resources.AuditRepository;
import resources.CounterReadingsRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Data
public class CounterReadingsServiceImpl implements CounterReadingsService {
    /**
     * хранилище пользователей
     */
    private final Map<Integer, User> userRepository;
    /**
     * хранилище показаний счетчиков
     */
    private final Map<Integer, CounterReadings> counterReadingsRepository;


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
    public void create(Integer userId, String counterType, Double readings) {
        if (userRepository.containsKey(userId)) {
            if (CounterTypeMap.getCounterTypeMap().containsValue(counterType)) {
                final Integer id = CounterReadingsRepository.getId();
                final LocalDate localDate = LocalDate.now();
                final int month = localDate.getMonthValue();
                final CounterReadings save = new CounterReadings(id, counterType, userId, month, readings);
                if (!counterReadingsRepository.containsValue(save)) {
                    counterReadingsRepository.put(id, save);
                    final String action = "createCounterReadings, counter tye : "
                            + counterType
                            + "; month "
                            + month + "; readings : "
                            + readings;
                    AuditRepository.addRepository(userId, action);
                }
                else throw new CounterReadingsException("The testimony can be submitted once a month!");
            } else throw new CounterReadingsException("This is counter type not found");


        } else throw new UserException("User not found");

    }

    /**
     * Метод получения актуальных показаний счетчиков
     * @param userId лицевой счет пользователя
     * @return список актуальныхпоказаний счетчиков за последний месяц
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     */
    @Override
    public List<CounterReadings> readActualReadings(Integer userId) {
        if(userRepository.containsKey(userId)) {
            Collection<CounterReadings> values = counterReadingsRepository.values();
            final Map<String, List<CounterReadings>> groupingByCounterType = values.stream()
                    .filter($ -> $.getId() == userId)
                    .collect(Collectors.groupingBy($ -> $.getCounterType()));
            final List<CounterReadings> result = groupingByCounterType.values().stream()
                    .map($ -> $.stream().max((a, b) -> a.getMonth() - b.getMonth()).get())
                    .collect(Collectors.toList());
            AuditRepository.addRepository(userId, "readActualReadings");

            return  result;
        }
        else throw new UserException("User not found");

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
    public List<CounterReadings> readMonthReadings(Integer userId, int month) {
        if(userRepository.containsKey(userId)) {
            final List<CounterReadings> result = counterReadingsRepository.values().stream()
                    .filter($ -> ($.getId() == userId) && ($.getMonth() == month))
                    .collect(Collectors.toList());
            if(result.isEmpty()) {
                throw new CounterReadingsException("There are no counter readings for this month");
            }
            AuditRepository.addRepository(userId, "readMonthReadings, month : " + month);

            return result;
        }
        else throw new UserException("User not found");

    }

    /**
     * Метод вывода итории подачи показаний сгруппированных по типу
     * @param userId лицевой счет пользователя
     * @return все показания пользователя сгруппированные по типу
     * @throw UserException("User not found") в случае если пользователь c таким лицевым счтом отсутствует
     * @throw CounterReadingsException("There are no counter readings") в случае если показания счечиков отсутствуют
     */
    @Override
    public Map<String, List<CounterReadings>> readHistoryReadings(Integer userId) {
        if (userRepository.containsKey(userId)) {
            final Map<String, List<CounterReadings>> result = counterReadingsRepository.values().stream()
                    .filter($ -> $.getId() == userId)
                    .collect(Collectors.groupingBy($ -> $.getCounterType()));
            AuditRepository.addRepository(userId, "readHistoryReadings");
            if(result.isEmpty()) {
                throw new CounterReadingsException("There are no counter readings");
            }

            return result;
        }
        else throw new UserException("User not found");
    }

}
