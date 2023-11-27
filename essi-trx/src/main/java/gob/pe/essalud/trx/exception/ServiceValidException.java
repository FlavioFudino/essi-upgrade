package gob.pe.essalud.trx.exception;

public class ServiceValidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1L;

    /**
     *
     */
    public ServiceValidException() {
        super();
    }

    /**
     * @param message
     */
    public ServiceValidException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceValidException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceValidException(Throwable cause) {
        super(cause);
    }
}
