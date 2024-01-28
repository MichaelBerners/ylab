package util;

import domain.entity.User;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * класс создающий хранилище пользователей
 */
public class UserRepository {
    private static final Map<Integer, User> USER_REPOSITORY = new HashMap<>();
    private static final AtomicInteger USER_ID_HOLDER = new AtomicInteger();
    public static Map<Integer, User> getUserRepository() {
         return USER_REPOSITORY;
    }
    public static int getId() {
        return USER_ID_HOLDER.incrementAndGet();
    }


}
