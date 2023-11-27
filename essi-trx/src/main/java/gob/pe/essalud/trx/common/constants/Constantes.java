package gob.pe.essalud.trx.common.constants;

public final class Constantes {

    private Constantes() {

    }

    public static final String URL_ENDPOINT_BASE_EMAIL_SERVICE = "base-url.email-service";
    public static final String URL_ENDPOINT_SMS_SERVICE = "base-url.sms-service";

    public static final String REGISTRAR_USUARIO = "miconsulta/registrarUsuario";
    public static final String RECUPERAR_CLAVE_WEB = "miconsulta/recuperarClaveWeb";
    public static final String RECUPERAR_CLAVE_MOBILE = "miconsulta/recuperarClaveMobile";

    public static final String FORMATO_FECHA_LARGA = "dd/MM/YYYY HH:mm:ss";
    public static final String URL_RECOVERY_PASSWORD_ENDPOINT = "clave.recovery.url";
    public static final String URL_RECOVERY_PASSWORD_PATH = "clave.recovery.recurso";
    public static final String URL_RECOVERY_ORIGIN_APK = "apk";

    //SMS SERVICE
    public static final String URL_SMS_SEND = "smsSendGet";
    public static final String PARAM_SMS_SEND_MOBILE = "mobile";
    public static final String PARAM_SMS_SEND_COUNTRY = "country";
    public static final String PARAM_SMS_SEND_MESSAGE = "message";
    public static final String PARAM_SMS_SEND_MESSAGE_FORMAT = "messageFormat";

}
