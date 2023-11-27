package gob.pe.essalud.emailservice.common.Enum;

public enum SomosEssaludEmailTemplate {
    REGISTRATION_CODE("activation_token"),
    RESET_PASSWORD("reset_password");

    public final String value;

    SomosEssaludEmailTemplate(String value) {
        this.value = value;
    }
}
