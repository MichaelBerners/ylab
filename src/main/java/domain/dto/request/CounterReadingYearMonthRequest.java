package domain.dto.request;

import lombok.Data;

@Data
public class CounterReadingYearMonthRequest {
    private Long userId;
    private int year;
    private int month;
}
