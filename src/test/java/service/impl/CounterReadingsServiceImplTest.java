package service.impl;

import domain.entity.CounterReadings;
import domain.entity.User;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CounterReadingsServiceImplTest {

    @Mock
    Map<Integer, User> userRepository;
    @Spy
    Map<Integer, CounterReadings> counterReadingsRepository = new HashMap<>();

    @InjectMocks
    CounterReadingsServiceImpl counterReadingsService;

    @BeforeEach
    void addToCounterRepository() {
        counterReadingsRepository.put(1, new CounterReadings(1, "HOT_WATER", 1, 1, 122.0));
        //counterRepository.put(2, new Counter(1, "COLD_WATER", 1, 1, 123.0));
        counterReadingsRepository.put(3, new CounterReadings(1, "HEATING", 1, 1, 124.0));
        System.out.println();
    }

    @Test
    void createShouldReturnPositive() {
        Integer userId = 1;
        String counterType = "COLD_WATER";
        Double readings = 124.1;
        when(userRepository.containsKey(1)).thenReturn(true);
        counterReadingsService.create(userId, counterType, readings);
        verify(counterReadingsRepository, times(2)).put(any(), any());

    }

    @Test
    void createShouldReturnException() {
        Integer userId = 2;
        String counterType = "NEW_COUNTER";
        Double readings = 144.0;
        //when(userRepository.containsKey(1)).thenReturn(true);
        when(userRepository.containsKey(2)).thenReturn(false);
        //when(userRepository.containsKey(3)).thenReturn(true);
//        assertAll(
//                () -> assertThrows(UserException.class, () -> counterReadingsService.create(userId, counterType, readings)),
//                () -> assertThrows(CounterReadingsException.class, () -> counterReadingsService.create(3, counterType,readings)),
//                () -> assertThrows(CounterReadingsException.class, () -> counterReadingsService.create(1, "HOT_WATER", readings))
//        );
        assertThrows(UserException.class, () -> counterReadingsService.create(userId, counterType, readings));

    }


}