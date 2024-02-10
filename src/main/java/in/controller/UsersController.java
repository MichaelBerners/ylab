package in.controller;

import domain.dto.request.UserCreateRequest;
import lombok.Data;
import service.UserService;

@Data
public class UsersController {

    private final UserService userService;

    public void create(UserCreateRequest userCreateRequest) {

        userService.create(userCreateRequest);
    }
}
