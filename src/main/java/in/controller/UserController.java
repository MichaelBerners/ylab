package in.controller;

import service.UserService;
import service.impl.UserServiceImpl;
import util.UserRepository;

import java.util.Scanner;

/**
 * класс имитирующий работу контроллера UserController
 */
public class UserController {
    static private final UserService userService = new UserServiceImpl(UserRepository.getUserRepository());

    /**
     * эндпоинт по созданию нового пользователя
     */
    public static void createNewUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите имя пользователя");
        final String lastName = scanner.nextLine();
        System.out.println("Ведите фамилию пользователя");
        final String firstName = scanner.nextLine();
        System.out.println("Ведите email пользователя");
        final String email = scanner.nextLine();
        System.out.println("Ведите password пользователя");
        final String password = scanner.nextLine();
        userService.create(lastName, firstName, email, password);
    }
    /**
     * эндпоинт авторизации пользователя
     */
    public static void authorizationUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите email пользователя");
        final String email = scanner.nextLine();
        System.out.println("Ведите password пользователя");
        final String password = scanner.nextLine();
        userService.authorization(email, password);

    }
}
