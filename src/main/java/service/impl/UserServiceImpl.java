package service.impl;

import domain.entity.User;
import domain.entity.UserRole;
import domain.exception.UserException;
import lombok.Data;
import service.UserService;
import resources.AuditRepository;
import resources.UserRepository;

import java.util.Map;


@Data
public class UserServiceImpl implements UserService {
    /**
     * хранилище пользователей
     */
    private final Map<Integer, User> userRepository;


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

        final User save = new User();
        final int id = UserRepository.getId();
        save.setId(id);
        save.setUserRole(UserRole.CLIENT);
        save.setFirstName(firstName);
        save.setLastName(lastName);
        save.setEmail(email);
        save.setPassword(password);
        if(!userRepository.values().contains(save)) {
            userRepository.put(id, save);
            System.out.println("registration was successful, personal account number : " + id);

        }
        else throw new UserException("The user exists!");
    }

    /**
     * метод авторизации пользователя (проверка по email и password)
     * @param email email
     * @param password password
     */
    @Override
    public void authorization(String email, String password) {
        User userAuthorization = userRepository.values().stream()
                .filter($ -> $.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
        if(userAuthorization.getPassword().equals(password)) {
            System.out.println("Authorization is successful!!!");
            AuditRepository.addRepository(userAuthorization.getId(), "authorization");

        }
        else System.out.println("The password is incorrect");
    }

    /**
     * метод контроля прав пользователя
     * @param id id пользователя
     * @return выводит права пользователя
     */
    @Override
    public UserRole getUserRole(Integer id) {
        final User userResult = userRepository.values().stream()
                .filter($ -> $.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));

        final UserRole result = userResult.getUserRole();

        return result;
    }
}
