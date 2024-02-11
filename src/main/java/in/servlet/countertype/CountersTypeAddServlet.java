package in.servlet.countertype;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.request.CounterTypeRequest;
import domain.dto.response.CounterTypeResponse;
import in.controller.CounterTypesController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/counters_type/add")
public class CountersTypeAddServlet extends HttpServlet {

    private final CounterTypesController counterTypesController = CounterTypesController.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = req.getReader();
             PrintWriter writer = resp.getWriter()) {

            ObjectMapper objectMapper = new ObjectMapper();
            CounterTypeRequest counterTypeRequest = objectMapper.readValue(reader, CounterTypeRequest.class);
            CounterTypeResponse counterTypeResponse = counterTypesController.create(counterTypeRequest);

            writer.write(objectMapper.writeValueAsString(counterTypeResponse));


        }
    }
}
