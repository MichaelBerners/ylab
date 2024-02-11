package in.servlet.counterreadings;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.request.CounterReadingYearMonthRequest;
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
import java.util.List;

@WebServlet("/counters_readings/read/date")
public class CountersReadingsReadDateServlet extends HttpServlet {

    private final CountersReadingsController countersReadingsController =
            CountersReadingsController.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (BufferedReader reader = req.getReader();
             PrintWriter writer = resp.getWriter()){
            //String collect = reader.lines().collect(Collectors.joining());
            ObjectMapper objectMapper = new ObjectMapper();

            CounterReadingYearMonthRequest counterReadingYearMonthRequest =
                    objectMapper.readValue(reader, CounterReadingYearMonthRequest.class);

            List<CounterReadingResponse> counterReadingDateResponses =
                    countersReadingsController.readYearMonthReadings(counterReadingYearMonthRequest);
            writer.write(objectMapper.writeValueAsString(counterReadingDateResponses));


        }
    }
}
