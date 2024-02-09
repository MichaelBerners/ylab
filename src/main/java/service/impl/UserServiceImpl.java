package service.impl;

import domain.dao.UserDao;
import domain.entity.User;

import domain.exception.UserException;
import lombok.Data;
import service.UserAuditService;
import service.UserService;


@Data
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserAuditService userAuditService;

    /**
     * Метод авторизации (создания нового) пользователя с правами по умолчанию : UserRole.CLIENT
     * @param firstName имя пользователя
     * @param lastName фамилия пользователя
     * @param email - email
     * @param password - password
     * @throw UserException("The user exists!") в случае если пользователь существует(проверка по полю email)
     */
    @Override
    public void create(String firstName, String lastName, String email, String password) {
        User createdUser = userDao.create(firstName)
                .orElseThrow(() -> new UserException("the user has not been created"));
        userAuditService.create(createdUser.getId(), "creating a user");
        System.out.println("registration was successful");
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
