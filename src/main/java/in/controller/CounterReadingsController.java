package in.controller;

import domain.dao.CounterReadingsDao;
import domain.dao.UserAuditDao;
import domain.entity.CounterReadings;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import service.CounterReadingsService;
import service.impl.CounterReadingsServiceImpl;
import resources.CounterReadingsRepository;
import resources.UserRepository;
import service.impl.UserAuditServiceImpl;
import util.ConnectionManager;


import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 * класс имитирующий работу контроллера CounterReadingsController
 */
public class CounterReadingsController {

    private final static CounterReadingsService counterReadingsService =
            new CounterReadingsServiceImpl(
                    new UserAuditServiceImpl(new UserAuditDao(ConnectionManager.getConnection())),
                    new CounterReadingsDao(ConnectionManager.getConnection()));

    /**
     * эндпоинт по созданию нового показания счетчика
     */
    public static void createNewCounterReadings() {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Ведите номер лицевого счета");
        final Long userId = scanner.nextLong();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        System.out.println("Введите тип счетчика");
        final String counterTye = scanner.nextLine();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        System.out.println("Ведите показания");
        final Double readings = scanner.nextDouble();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        counterReadingsService.create(userId, counterTye, readings);
    }

    /**
     * эндпоинт по получению актуальных показаний счетчика
     */
    public static void readActualReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите номер лицевого счета");
        final Long userId = scanner.nextLong();
        List<CounterReadings> actualReadings = counterReadingsService.readActualReadings(userId);
        actualReadings.forEach(System.out::println);
    }

    /**
     * эндпоинт по получению показаний счетчика за определенный месяц
     */
    public static void readMonthReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер лицевого счета");
        Long userId = scanner.nextLong();
        System.out.println("Введите год");
        Integer year = scanner.nextInt();
        System.out.println("Введите номер месяца 1-12");
        Integer month = scanner.nextInt();
        List<CounterReadings> monthReadings = counterReadingsService.readYearMonthReadings(userId, year, month);
        monthReadings.forEach(System.out::println);
    }

    /**
     * эндпоинт по получению всех показаний счетчиков у конкретного человека
     */
    public static void readHistoryReadings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер лицевого счета");
        Long userId = scanner.nextLong();
        List<CounterReadings> historyReadings = counterReadingsService.readHistoryReadings(userId);
        historyReadings.forEach(System.out::println);
    }

}
