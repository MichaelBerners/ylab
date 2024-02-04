package service;


public interface UserService {

    /**
     * Метод авторизации (создания нового) пользователя с правами по умолчанию : UserRole.CLIENT
     * @param firstName имя пользователя
     * @param lastName фамилия пользователя
     * @param email - email
     * @param password - password
     * @throw UserException("The user exists!") в случае если пользователь существует(проверка по полю email)
     */
    void create(String firstName, String lastName, String email, String password);
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
