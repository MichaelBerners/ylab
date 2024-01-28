import in.controller.CounterReadingsController;
import in.controller.UserController;

import java.util.Scanner;

/**
 * класс запускающий консольное приложение
 * для подачи показаний счетчиков отопления, горячей и холодной воды
 */
public class Runner {
    public static void main(String[] args) {
        int number = 0;

        do {
            System.out.println("Справка :");
            System.out.println("1. регистрация");
            System.out.println("2. авторизация");
            System.out.println("3. подача показаний счетчиков");
            System.out.println("4. узнать актуальные показания счетчиков");
            System.out.println("5. узнать показания счетчиков за опредеенный месяц");
            System.out.println("6. просмотр всех поданых показаний");
            System.out.println("7. выход");
            Scanner scan = new Scanner(System.in);
            number = scan.nextInt();
            switch (number) {
                case 1 :
                    UserController.createNewUser();
                    break;
                case 2 :
                    UserController.authorizationUser();
                    break;
                case 3 :
                    CounterReadingsController.createNewCounterReadings();
                    break;
                case 4 :
                    CounterReadingsController.readActualReadings();
                    break;
                case 5 :
                    CounterReadingsController.readMonthReadings();
                    break;
                case 6 :
                    CounterReadingsController.readHistoryReadings();
                    break;
            }

        }
        while (number >= 1 && number <= 6);
    }

}
