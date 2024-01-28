package service.impl;

import domain.entity.User;
import domain.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Spy
    Map<Integer, User> userRepository = new HashMap<>();

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void addToCounterRepository() {
        userRepository.put(10, new User(10, UserRole.CLIENT, "firstName", "lastName", "email", "password"));
        System.out.println();
    }

    @Test
    void createShouldReturnPositive() {
        String firstName = "a";
        String lastName = "b";
        String email = "c";
        String password = "d";

        userService.create(firstName, lastName, email, password);
        verify(userRepository, times(2)).put(any(),any());
    }

}