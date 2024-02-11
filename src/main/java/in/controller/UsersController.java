package in.controller;

import domain.dao.impl.UserAuditDaoImpl;
import domain.dao.impl.UserDaoImpl;
import domain.dto.request.UserCreateRequest;
import lombok.Data;
import service.UserAuditService;
import service.UserService;
import service.impl.UserAuditServiceImpl;
import service.impl.UserServiceImpl;
import util.ConnectionManager;

import java.sql.Connection;

@Data
public class UsersController {

    private static final UsersController INSTANCE = new UsersController(new UserServiceImpl(
            new UserDaoImpl(ConnectionManager.getConnection()),new UserAuditServiceImpl(new UserAuditDaoImpl(ConnectionManager.getConnection()))));

    private final UserService userService;

    public static UsersController getInstance() {
        return INSTANCE;
    }



    public void create(UserCreateRequest userCreateRequest) {

        userService.create(userCreateRequest);
    }
}
