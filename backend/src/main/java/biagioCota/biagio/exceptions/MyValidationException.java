package biagioCota.biagio.exceptions;

public class MyValidationException extends RuntimeException {
    public MyValidationException(String message) {
        super(message);
    }

    public MyValidationException() {
        super("Validazione non riuscita!");
    }
}
