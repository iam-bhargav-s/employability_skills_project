package exceptions;

public class InvalidServiceStatusException extends Exception {
    public InvalidServiceStatusException(String message) {
        super(message);
    }
}
