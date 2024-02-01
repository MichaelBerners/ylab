package resources;

import domain.entity.CounterReadings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * класс создающий хранилище показаний счетчиков
 */
public class CounterReadingsRepository {
    private static final Map<Integer, CounterReadings> COUNTER_READINGS_REPOSITORY = new HashMap<>();
    private static final AtomicInteger CLIENTREADINGS_ID_HOLDER = new AtomicInteger();
    public static Map<Integer, CounterReadings> getCounterReadingsRepository() {
        return COUNTER_READINGS_REPOSITORY;
    }
    public static int getId() {
        return CLIENTREADINGS_ID_HOLDER.incrementAndGet();
    }
}
