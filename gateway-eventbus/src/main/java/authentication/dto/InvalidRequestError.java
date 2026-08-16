package authentication.dto;

public class InvalidRequestError extends RuntimeException {
    public InvalidRequestError(String message) {
        super(message);
    }
}
