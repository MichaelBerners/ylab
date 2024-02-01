package in.controller;

import domain.entity.CounterReadings;
import service.CounterReadingsService;
import service.impl.CounterReadingsServiceImpl;
import resources.CounterReadingsRepository;
import resources.UserRepository;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * класс имитирующий работу контроллера CounterReadingsController
 */
public class CounterReadingsController {

    private final static CounterReadingsService counterReadingsService = new CounterReadingsServiceImpl(
            UserRepository.getUserRepository(),
            CounterReadingsRepository.getCounterReadingsRepository()
    );

    /**
     * эндпоинт по созданию нового показания счетчика
     */
    public static void createNewCounterReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите номер лицевого счета");
        final Integer userId = scanner.nextInt(2);
        System.out.println();
        System.out.println("lol");
        System.out.println("Введите тип счетчика");
        final String counterTye = scanner.nextLine();
        System.out.println("Ведите показания");
        final Double readings = scanner.nextDouble();
        counterReadingsService.create(userId, counterTye, readings);
    }

    /**
     * эндпоинт по получению актуальных показаний счетчика
     */
    public static void readActualReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите номер лицевого счета");
        final Integer userId = scanner.nextInt();
        List<CounterReadings> actualReadings = counterReadingsService.readActualReadings(userId);
        actualReadings.forEach(System.out::println);
    }

    /**
     * эндпоинт по получению показаний счетчика за определенный месяц
     */
    public static void readMonthReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер лицевого счета");
        Integer userId = scanner.nextInt();
        System.out.println("Введите номер месяца");
        Integer month = scanner.nextInt();
        List<CounterReadings> monthReadings = counterReadingsService.readMonthReadings(userId, month);
        monthReadings.forEach(System.out::println);
    }

    /**
     * эндпоинт по получению всех показаний счетчиков у конкретного человека
     */
    public static void readHistoryReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер лицевого счета");
        Integer userId = scanner.nextInt();
        Map<String, List<CounterReadings>> historyMap = counterReadingsService.readHistoryReadings(userId);
        for (Map.Entry<String, List<CounterReadings>> x : historyMap.entrySet()) {
            System.out.println(x.getKey());
            x.getValue().forEach(System.out::println);
        }

    }

}
