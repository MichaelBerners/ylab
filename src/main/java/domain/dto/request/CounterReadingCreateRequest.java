package domain.dto.request;

import lombok.Data;

@Data
public class CounterReadingCreateRequest {
    private Long userId;
    private String counterType;
    private Double readings;
}
