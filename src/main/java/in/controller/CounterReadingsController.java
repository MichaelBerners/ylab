package in.controller;

import domain.entity.CounterReadings;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import service.CounterReadingsService;
import service.impl.CounterReadingsServiceImpl;
import util.CounterReadingsRepository;
import util.UserRepository;

import java.sql.SQLOutput;
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
        try {
            counterReadingsService.create(userId, counterTye, readings);
        }
        catch (CounterReadingsException e) {
            e.getMessage();
        }
        catch (UserException e) {
            e.getMessage();
        }
    }

    /**
     * эндпоинт по получению актуальных показаний счетчика
     */
    public static void readActualReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите номер лицевого счета");
        final Integer userId = scanner.nextInt();
        List<CounterReadings> actualReadings = counterReadingsService.readActualReadings(userId);
        try {
            actualReadings.forEach(System.out::println);
        }
        catch (UserException e){
            e.printStackTrace();
        }
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
        try {
            monthReadings.forEach(System.out::println);
        }
        catch (CounterReadingsException e) {
            e.getMessage();
        }
        catch (UserException e) {
            e.getMessage();
        }
    }

    /**
     * эндпоинт по получению всех показаний счетчиков у конкретного человека
     */
    public static void readHistoryReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер лицевого счета");
        Integer userId = scanner.nextInt();
        Map<String, List<CounterReadings>> historyMap = counterReadingsService.readHistoryReadings(userId);
        for(Map.Entry<String, List<CounterReadings>> x : historyMap.entrySet()) {
            System.out.println(x.getKey());
            x.getValue().forEach(System.out::println);
        }

    }

}
