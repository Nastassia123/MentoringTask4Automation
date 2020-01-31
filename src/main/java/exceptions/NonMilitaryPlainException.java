package exceptions;

public class NonMilitaryPlainException extends Exception {


    public NonMilitaryPlainException() {
    }

    public NonMilitaryPlainException(String message) {
        super(message);
    }

    public NonMilitaryPlainException(String message, Throwable exception) {
        super(message, exception);
    }

    public NonMilitaryPlainException(Throwable exception) {
        super(exception);
    }
}