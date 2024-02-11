package in.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.request.UserCreateRequest;
import in.controller.UsersController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/users/*"})
public class UserServlet extends HttpServlet {

    private final UsersController usersController = UsersController.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        switch (url) {
            case "/users/add" :
                BufferedReader reader = req.getReader();
                System.out.println();
                ObjectMapper objectMapper = new ObjectMapper();
                UserCreateRequest userCreateRequest = objectMapper.readValue(reader, UserCreateRequest.class);
                usersController.create(userCreateRequest);
                resp.setStatus(HttpServletResponse.SC_OK);
                System.out.println();
        }
        System.out.println();
    }
}
