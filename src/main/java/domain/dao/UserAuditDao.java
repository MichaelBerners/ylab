package domain.dao;

public interface UserAuditDao {

    void create(Long userId, String action);
}
