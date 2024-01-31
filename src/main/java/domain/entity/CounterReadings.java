package domain.entity;

import lombok.*;


/**
 * Класс описывающий показания счетчика
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterReadings {

    @EqualsAndHashCode.Exclude
    private Integer id;
    private String counterType;
    private Integer userId;
    private Integer month;
    @EqualsAndHashCode.Exclude
    private Double readings;

}
