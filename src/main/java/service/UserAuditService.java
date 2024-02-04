package service;

import java.sql.Connection;

public interface UserAuditService {

    /**
     * создание записи действия пользователя
     * @param userId id пользователя
     * @param action действие совершенное пользователем
     */
    void create(Long userId, String action);
}
