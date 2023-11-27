package gob.pe.essalud.client.common.constants;

public final class Constantes {

    private Constantes() {

    }

    public static final String RETORNO_EXITO_TRX = "1";
    public static final String RETORNO_EXITO = "0";
    public static final String COD_OPCION = "1";
    public static final String INFO_URL = "->URL:";

    public static final String FILTER_TODOS = "TODOS";
    public static final String LOG_LEVEL_INFO = "info";

    public static final String APP_VERSION_NUMBER = "application.version";

    /* Recursos*/

    public static final String URL_CANCELA_SOLICITUD = "pCancelaSolCitaRWs";
    public static final String URL_CREAR_SOLICITUD = "pCreaSolCitaEssiRWs";
    public static final String URL_LISTA_REFERENCIA = "pListaReferenRWs";


    public static final String URL_ENDPOINT_BASE_CONSULTA_CITT_SERVICE = "base.consulta-citt";
    public static final String CONSULTA_CITT_BUSCAR = "v1/{tipoDoc}/{numDoc}";


    /*Nuevos Servicios de essi*/
    public static final String URL_ENDPOINT_CONSULTAS_ESSI = "essi.consulta.url";
    public static final String URL_ENDPOINT_CITA_ESSI = "essi.cita.url";
    public static final String URL_ENDPOINT_MEDICO_ESSI = "essi.medico.url";

    public static final String URL_CONSULTA_EXTERNA = "consAtenCextWs";
    public static final String URL_CONSULTA_EMERGENCIA = "consAtenEmerRWs"; //antiguo era: consAtenEmerWs
    public static final String URL_CONSULTA_HOSPITALIZACION = "consAtenHospWs";
    public static final String URL_CONSULTA_CENTRO_QUIRURGICO = "consAtenCeqxWs";
    public static final String URL_CONSULTA_RECETAS = "pRecePacRWs";
    public static final String URL_CONSULTA_ATENCIONES_MEDICAS = "pConsAtenCextCittWs"; //CITT

    public static final String URL_CONSULTA_ATENCIONES_MEDICAS_CITT = "pConsAtenCextCittWs"; //CITT
    public static final String URL_CONSULTA_LISTA_SOLICITUD_CITT = "pLisSolRegCittWs"; //CITT
    public static final String URL_CONSULTA_DETALLE_SOLICITUD_CITT = "pDetSolRegCittWs"; //CITT

    /*Migrados a nuevo Proyecto*/
    public static final String URL_LOGIN_MOVIL = "pLoginMovilRWs";
    public static final String URL_CITAS_EMITIDAS = "pCitasCextRWs";
    public static final String URL_PARAM_SOLICITUD = "pParmSolCitaEssiRWs";
    public static final String URL_REGISTRAR_SOLICITUD_CITT = "pRegSolRegCittWs";
    public static final String URL_PROGRAMACION_DISPONIBLE = "pBusProgDispEssiRWs";
    public static final String URL_PROGRAMACION_SOLICITUD = "pBusProgAteSolRWs";
    public static final String URL_GENERAR_CITA = "pGenCitaDispRWs";
    public static final String URL_GENERAR_CITA_SOLICITUD = "pGenCitaAteSolRWs";
    public static final String URL_ELIMINAR_CITA = "pCitaEliWs";
    public static final String URL_ACT_DAT_CONTACT_PAC = "pActDatContacPacRWs";
    public static final String URL_ACTUALIZAR_DATOS_PERSONA = "pActDatCelEmailRWs";
    public static final String URL_LISTA_SOLICITUD = "pListaSolCitaEssiRWs";

    /*Para Recursos de Essi-Medico*/
    public static final String URL_LOGIN_MEDICO = "getLogin";
    public static final String URL_PROGRAMACION_MEDICO = "getProgramacion";
    public static final String URL_PACIENTE_MEDICO = "getPacientesCitados";
    public static final String URL_EXAMENES_AUXILIARES = "listaSolicitudExamen";
    /*End Point Farmacias*/
    public static final String URL_ENDPOINT_FARMACIAS_LISTA = "farmacia.consulta.path.url";
    public static final String URL_ENDPOINT_FARMACIAS_TRACKING = "farmacia.tracking.path.url";
    public static final String URL_ENDPOINT_FARMACIAS_BASE = "farmacia.base.path.url";
    /*Recursos Farmacias*/
    public static final String URL_FARMACIAS_LISTAR = "conspharmacy";
    public static final String URL_FARMACIAS_REG_PACIENTE = "reg-asegurado-pes-mob";
    public static final String URL_FARMACIAS_CON_PACIENTE = "consulta-asegurado-fv";
    public static final String URL_FARMACIAS_USUARIO = "2024";
    public static final String URL_FARMACIAS_STATUS = "1";
    public static final String URL_FARMACIAS_SERVICE = "6";
    public static final String URL_FARMACIAS_INSERT = "INSERT";
    public static final String URL_FARMACIAS_UPDATE = "UPDATE";
    public static final String URL_FARMACIA_VECINA = "tracking-recetas-fv";
    /*Familiar*/
    public static final String URL_FAMILIAR = "familiar";
    public static final String URL_OTROS_CONTACTOS = "otros-contactos";
    public static final String URL_USUARIOS_PERFIL = "usuarios/perfil";
    public static final String URL_CENTRO_GET = "centros";

    public static final String URL_CONSULTA_CENTRO_LAT_LONG = "p/ccll/{cenAsiCod}";

    /*Recursos Ubigeo*/
    public static final String URL_UBIGEO_LIST = "getListUbigeo";
    public static final String URL_DEPARTAMENTOS = "departamentos";
    public static final String URL_PROVINCIAS = "provincias";
    public static final String URL_DISTRITOS = "distritos";
    /*Version de la Aplicacion Movil*/
    public static final String URL_ENDPOINT_VERSION_APPS = "application.version.url";
    /*Versión Oauth*/
    public static final String URL_ENDPOINT_OAUTH = "oauth.path.url";
    /*Recursos Version*/
    public static final String URL_RECURSO_SIGLAS = "siglas";
    public static final String URL_PARAM_SIGLAS = "siglas";
    public static final String URL_PARAMETROS = "parametros";
    public static final String URL_PARAMETROS_FILTERS = "filters";
    /*Tipo de Documento*/
    public static final String URL_TIPO_DOCUMENTO = "tipoDocumento";
    public static final String URL_NRO_DOCUMENTO = "numeroDocumento";
    /*End Point Token*/
    public static final String URL_SEND_TOKEN = "sendToken";
    /*End Point Captcha*/
    public static final String URL_ENDPOINT_CAPTCHA = "captcha.path.url";
    /*Oauth*/
    public static final String URL_TOKEN = "token";
    public static final String URL_TOKEN_USER_SECURITY = "Basic ZXNzaS1zZXJ2aWNlczpFc3NhbHVk";
    public static final String URL_GRANT_TYPE = "grant_type";
    public static final String URL_USERNAME = "username";
    public static final String URL_ACCESS = "password";
    public static final String URL_RETOKEN = "refresh_token";
    /*Recursos Version*/
    /*Token-Registro y Usuarios*/

    /*Base URL ESSI-TRX */
    public static final String URL_ENDPOINT_BASE_TRX = "base.trx.path.url";
    public static final String URL_PACIENTE_GET = "paciente/getPacienteByNumero";
    public static final String URL_PACIENTE_DIRECCION_GET = "paciente/getDireccionFarmacia";
    public static final String URL_PACIENTE_SAVE = "paciente/saveFarmaciaPaciente";
    public static final String URL_CENTROS_APPLY_CITA = "centros/applyCita";

    /*Recursos TRX*/
    public static final String URL_PATH_CLAVE = "clave";
    public static final String URL_PATH_RECOVERY = "clave/recovery";

    public static final String URL_EXIST_USER = "/exist";
    public static final String URL_USUARIOS = "/usuarios";
    public static final String URL_TOKEN_ACTIVAR = "activar";
    public static final String URL_TOKEN_VALIDAR = "validar";
    public static final String URL_TOKEN_EXISTE_ACTIVO = "existe-activo";
    public static final String URL_SEGURIDAD_CLIENTE = "/seguridad-cliente";
    public static final String URL_SEGURIDAD_CLIENTE_GUARDAR_DETALLE = "/guardar-detalle";
    public static final String URL_PARAMETROS_TRX = "/parametros";
    public static final String URL_PARAMETROS_TRX_CURRENT_DATE = "/current-date";
    public static final String URL_BIENVENIDA_TRX = "/bienvenida";
    public static final String URL_GUARDAR_CITA = "cita/guardar";
    public static final String URL_BUSCAR_CITAS = "cita/buscar";
    public static final String URL_BI_CONSULTA_USUARIOS = "bi/consulta-usuarios";

    /*RENIEC*/
    public static final String URL_ENDPOINT_RENIEC = "reniec.path.url";
    public static final String URL_CONSULTA_DNI = "reniec/consulta/dni/";

    /*BÙSQUEDA ACTIVA*/
    public static final String URL_ENDPOINT_BUSQ_ACTIVA = "busq_activa.path.url";
    public static final String URL_ASEGURADO = "asegurado";
    public static final String URL_ASEGURADO_LOGIN = "asegurado/login";
    public static final String URL_CONDICION = "condicion";
    public static final String URL_CONDICION_SALUD = "condicion-salud";
    public static final String URL_FAMILIAR_DELETE = "familiar/delete";

    /* PLANTILLA QR */
    public static final String URL_ENDPOINT_PLATILLA_QR = "plantilla_qr.path.url";

    /*CAPTCHA*/
    public static final String URL_SITE_VERIFY = "siteverify";

    /*TRIAJE*/
    public static final String URL_ENDPOINT_BASE_TRIAJE = "triaje.path.url";
    public static final String URL_TRIAJE_AUTOEVALUACION = "/wsdl_AutoEvaluacion";
    public static final String URL_TRIAJE_USERNAME = "triaje.username";
    public static final String URL_TRIAJE_ACCESS = "triaje.password";

}
