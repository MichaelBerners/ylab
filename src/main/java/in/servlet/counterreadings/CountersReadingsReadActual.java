package in.servlet.counterreadings;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.response.CounterReadingResponse;
import in.controller.CountersReadingsController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/counters_readings/read/actual")
public class CountersReadingsReadActual extends HttpServlet {

    private final CountersReadingsController countersReadingsController =
            CountersReadingsController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(req.getParameter("id"));

        try (PrintWriter writer = resp.getWriter()) {
            List<CounterReadingResponse> counterReadingResponses = countersReadingsController.readActualReadings(id);

            ObjectMapper objectMapper = new ObjectMapper();

            writer.write(objectMapper.writeValueAsString(counterReadingResponses));

        }
    }
}
