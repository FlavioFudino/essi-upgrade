package gob.pe.essalud.trx.common.Enum;

public enum EmailTemplate {
    REGISTRATION_CODE("registration_code"),
    RESET_PASSWORD("reset_password"),
    RESET_PASSWORD_APK("reset_password_apk"),
    PADOMI_INSCRIPTION("padomi_inscription"),
    RECORDATORIO_CITA("recordatorio_cita");

    public final String value;

    EmailTemplate(String value) {
        this.value = value;
    }
}
