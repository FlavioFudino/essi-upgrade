package gob.pe.essalud.client.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String detail) {
        super(detail);
    }
}