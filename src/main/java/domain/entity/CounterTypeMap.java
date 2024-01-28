package domain.entity;

import domain.exception.UserException;
import util.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс описывающий хранилище типа счетчиков
 */
public class CounterTypeMap {
    /**
     * типы счетчиков
     */
    private static final Map<Integer, String> COUNTER_TYPE = new HashMap<>();
    /**
     * id типа счетчика
     */
    private static final AtomicInteger COUNTER_TYPE_ID = new AtomicInteger();
    /**
     * хранилище пользователей
     */
    private static final Map<Integer, User> userRepository = UserRepository.getUserRepository();

    /**
     * статический блок для создания типов счетчика
     */
    static {
        int id = COUNTER_TYPE_ID.incrementAndGet();
        COUNTER_TYPE.put(getId(), "HEATING");
        COUNTER_TYPE.put(getId(), "HOT_WATER");
        COUNTER_TYPE.put(getId(), "COLD_WATER");
    }

    /**
     * метод получения id счетчика
     * @return следующий id
     */
    private static int getId() {
        return COUNTER_TYPE_ID.incrementAndGet();
    }

    /**
     * вывод хранилища типов счетчика
     * @return типы счетчика
     */
    public static Map<Integer, String> getCounterTypeMap() {
        return COUNTER_TYPE;
    }

    /**
     * метод добавления новых типов счетчика
     * @param userId id пользователя
     * @param newCounterType новый тип счетчика
     * @throw UserException("User not found") в случае если пользователь не найден
     */
    public static void addCounterType(Integer userId, String newCounterType) {
        if(userRepository.containsKey(userId)) {
            User user = userRepository.get(userId);
            if(user.getUserRole().equals(UserRole.ADMINISTRATOR)) {
                COUNTER_TYPE.put(getId(), newCounterType);
            }  System.out.println("The action is not allowed!");
        }
        else throw new UserException("User not found");

    }

}
