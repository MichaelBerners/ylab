package service.impl;

import domain.dao.UserAuditDao;
import lombok.Data;
import service.UserAuditService;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
@Data
public class UserAuditServiceImpl implements UserAuditService {
    private final UserAuditDao userAuditDao;
    @Override
    public void create(Long userId, String action) {
        userAuditDao.create(userId, action);
    }
}
