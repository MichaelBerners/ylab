package service;


import domain.dto.request.UserCreateRequest;
import domain.dto.response.UserResponse;
import domain.entity.User;

public interface UserService {

    /**
     * Метод авторизации (создания нового) пользователя с правами по умолчанию : UserRole.CLIENT
     * @param firstName имя пользователя
     * @param lastName фамилия пользователя
     * @param email - email
     * @param password - password
     * @throw UserException("The user exists!") в случае если пользователь существует(проверка по полю email)
     */
    UserResponse create(UserCreateRequest userCreateRequest);
    /**
     * метод авторизации пользователя (проверка по email и password)
     * @param email email
     * @param password password
     */
    void authorization(String email, String password);
    /**
     * метод контроля прав пользователя
     * @param id id пользователя
     * @return выводит права пользователя
     */
    String getUserRole(Long id);
}
