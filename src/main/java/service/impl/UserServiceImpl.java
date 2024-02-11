package service.impl;

import domain.dao.UserDao;
import domain.dto.mapper.UserMapper;
import domain.dto.request.UserCreateRequest;
import domain.dto.response.UserResponse;
import domain.entity.User;

import domain.exception.UserException;
import lombok.Data;
import service.UserAuditService;
import service.UserService;


@Data
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserAuditService userAuditService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    /**
     * Метод авторизации (создания нового) пользователя с правами по умолчанию : UserRole.CLIENT

     * @throw UserException("The user exists!") в случае если пользователь существует(проверка по полю email)
     */
    @Override
    public UserResponse create(UserCreateRequest userCreateRequest) {
        String firstName = userCreateRequest.getFirstName();
        String lastName = userCreateRequest.getLastName();
        String email = userCreateRequest.getEmail();
        String password = userCreateRequest.getPassword();
        User createdUser = userDao.create(firstName, lastName, email, password);
        userAuditService.create(createdUser.getId(), "creating a user");
        UserResponse userResponse = userMapper.userToUserResponse(createdUser);
        System.out.println();

        return userResponse;

    }

    /**
     * метод авторизации пользователя (проверка по email и password)
     * @param email email
     * @param password password
     */
    @Override
    public void authorization(String email, String password) {
        User findUser = userDao.findUserByEmailAndPassword(email, password).orElseThrow(() -> new UserException("exc"));
        System.out.println("Authorization is successful!!!");
        userAuditService.create(findUser.getId(), "authorization");
    }

    /**
     * метод контроля прав пользователя
     * @param id id пользователя
     * @return выводит права пользователя
     */
    @Override
    public String getUserRole(Long id) {

        return userDao.getUserRoleByUserId(id);
    }
}
