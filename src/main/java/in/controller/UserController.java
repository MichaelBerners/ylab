package in.controller;

import domain.dao.UserAuditDao;
import domain.dao.UserDao;
import domain.exception.UserException;
import service.UserService;
import service.impl.UserAuditServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Scanner;

/**
 * класс имитирующий работу контроллера UserController
 */
public class UserController {
    static private final UserService userService =
            new UserServiceImpl(UserDao.getInstance(), new UserAuditServiceImpl(UserAuditDao.getInstance()));

    /**
     * эндпоинт по созданию нового пользователя
     */
    public static void createNewUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите имя пользователя");
        final String lastName = scanner.nextLine();
        if(lastName.length() == 0){
            throw new UserException("Имя не должно быть пустым");
        }
        System.out.println("Ведите фамилию пользователя");
        final String firstName = scanner.nextLine();
        if(firstName.length() == 0){
            throw new UserException("Фамилия не должна быть пустой");
        }
        System.out.println("Ведите email пользователя");
        final String email = scanner.nextLine();
        if(email.length() == 0){
            throw new UserException("email не должен быть пустым");
        }
        System.out.println("Ведите password пользователя");
        final String password = scanner.nextLine();
        if(password.length() == 0){
            throw new UserException("password не должен быть пустым");
        }
        userService.create(lastName, firstName, email, password);
    }
    /**
     * эндпоинт авторизации пользователя
     */
    public static void authorizationUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите email пользователя");
        final String email = scanner.nextLine();
        if(email.length() == 0){
            throw new UserException("email не должен быть пустым");
        }
        System.out.println("Ведите password пользователя");
        final String password = scanner.nextLine();
        if(password.length() == 0){
            throw new UserException("password не должен быть пустым");
        }
        userService.authorization(email, password);

    }
}
