package in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.request.UserCreateRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet({"/users", "/"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        switch (url) {
            case "/users/add" :
                BufferedReader reader = req.getReader();

                ObjectMapper objectMapper = new ObjectMapper();
                UserCreateRequest userCreateRequest = objectMapper.readValue(reader, UserCreateRequest.class);
                System.out.println();
        }
        System.out.println();
    }
}
