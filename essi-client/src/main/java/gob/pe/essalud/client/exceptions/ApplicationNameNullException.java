package gob.pe.essalud.client.exceptions;

public class ApplicationNameNullException extends RuntimeException {

    private static final long serialVersionUID = 4116705716089556302L;

    public ApplicationNameNullException() {
        super("spring.application.name is required for spring boot configuration in application.properties or application.yml");
    }

}