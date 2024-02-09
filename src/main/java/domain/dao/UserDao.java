package domain.dao;

import domain.dto.request.UserCreateRequest;
import domain.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> create(UserCreateRequest userCreateRequest);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    String getUserRoleByUserId(Long id);
}
