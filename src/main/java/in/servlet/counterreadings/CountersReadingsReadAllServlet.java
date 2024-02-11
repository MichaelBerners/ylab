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

//@WebServlet(urlPatterns = {"/counters_readings/*"})
@WebServlet("/counters_readings/read/all")
public class CountersReadingsReadAllServlet extends HttpServlet {

    private final CountersReadingsController countersReadingsController = CountersReadingsController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String requestURI = req.getRequestURI();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(req.getParameter("id"));
        List<CounterReadingResponse> counterReadingAllResponses = countersReadingsController.readHistoryReadings(id);

        try (PrintWriter writer = resp.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(counterReadingAllResponses));

//                    writer.print(objectMapper.writeValueAsString(counterReadingAllResponses));
//                    writer.flush();


//
        }

    }
}
