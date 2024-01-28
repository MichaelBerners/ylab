package domain.exception;

public class CounterReadingsException extends RuntimeException {
    public CounterReadingsException(String message) {
        super(message);
    }
}
