package domain.dao;

import domain.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> create(String firstName, String lastName, String email, String password);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    String getUserRoleByUserId(Long id);
}
