package service;

import java.sql.Connection;

public interface UserAuditService {

    void create(Long userId, String action);
}
