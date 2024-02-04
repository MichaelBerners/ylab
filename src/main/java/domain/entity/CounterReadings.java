package domain.entity;

import lombok.*;


/**
 * Класс описывающий показания счетчика
 */
@Data
public class CounterReadings {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String counterType;
    private Long userId;
    private Integer year;
    private Integer month;
    @EqualsAndHashCode.Exclude
    private Double readings;

}
