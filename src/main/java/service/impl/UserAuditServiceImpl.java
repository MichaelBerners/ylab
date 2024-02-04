package service.impl;

import domain.dao.UserAuditDao;
import lombok.Data;
import service.UserAuditService;

@Data
public class UserAuditServiceImpl implements UserAuditService {
    private final UserAuditDao userAuditDao;
    /**
     * создание записи действия пользователя
     * @param userId id пользователя
     * @param action действие совершенное пользователем
     */
    @Override
    public void create(Long userId, String action) {
        userAuditDao.create(userId, action);
    }
}
