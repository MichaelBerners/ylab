package domain.dto.response;


import lombok.Data;

@Data
public class CounterReadingResponse {
    private String counterType;
    private Long userId;
    private Integer year;
    private Integer month;
    private Double readings;
}
