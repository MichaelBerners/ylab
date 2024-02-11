package in.servlet.counterreadings;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.response.CounterReadingResponse;
import in.controller.CountersReadingsController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/counters_readings/create")
public class CountersReadingsCreateServlet extends HttpServlet {

    private final CountersReadingsController countersReadingsController = CountersReadingsController.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = req.getReader();
             PrintWriter writer = resp.getWriter()) {

            ObjectMapper objectMapper = new ObjectMapper();
            CounterReadingCreateRequest counterReadingCreateRequest =
                    objectMapper.readValue(reader, CounterReadingCreateRequest.class);
            CounterReadingResponse counterReadingResponse =
                    countersReadingsController.create(counterReadingCreateRequest);
            writer.write(objectMapper.writeValueAsString(counterReadingResponse));

        }
    }
}
