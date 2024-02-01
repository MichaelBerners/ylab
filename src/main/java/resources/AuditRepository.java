package resources;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * класс для ведения аудита действий пользователя
 */
public class AuditRepository {

    /**
     * хранилище действий
     */
    private final static Map<Integer, Map<String, LocalDateTime>> AUDIT_REPOSITORY = new HashMap<>();
    /**
     * добавление нового действия пользователя в хранилище
     * @param userId id пользователя
     * @param action действие совершенное пользователем
     */
    public static void addRepository(Integer userId, String action) {
        final Map<String, LocalDateTime> actionMap = new HashMap<>();
        final LocalDateTime localDateTime = LocalDateTime.now();
        actionMap.put(action, localDateTime);
        AUDIT_REPOSITORY.put(userId, actionMap);
    }
}
