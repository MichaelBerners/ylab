package domain.entity;

import lombok.*;

import java.util.Objects;

/**
 * Класс описывающий показания счетчика
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterReadings {

    //id
    @EqualsAndHashCode.Exclude
    private Integer id;
    //тип счетчика
    //private CounterType counterType;
    private String counterType;
    //лицевой счет клиента
    private Integer userId;
    //порялковый номер месяца от 1..12
    private Integer month;
    //показания счетчика
    @EqualsAndHashCode.Exclude
    private Double readings;

}
