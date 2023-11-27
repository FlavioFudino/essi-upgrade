package gob.pe.essalud.emailservice.common.Enum;

public enum MiConsultaEmailTemplate {
    REGISTRATION_CODE("registration_code"),
    RESET_PASSWORD("reset_password"),
    RESET_PASSWORD_APK("reset_password_apk");
    //PADOMI_INSCRIPTION("padomi_inscription")

    public final String value;

    MiConsultaEmailTemplate(String value) {
        this.value = value;
    }
}
