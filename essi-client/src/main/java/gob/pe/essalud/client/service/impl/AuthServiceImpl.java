package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.*;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.common.util.SecurityUtil;
import gob.pe.essalud.client.common.util.StringUtil;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.dto.CentroDto;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.RequestParametroDto;
import gob.pe.essalud.client.dto.essi.*;
import gob.pe.essalud.client.dto.trx.UpdateCentroPacienteRequestDto;
import gob.pe.essalud.client.dto.usuario.UsuarioRequestDto;
import gob.pe.essalud.client.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {

    private final RestTemplate restTemplate;
    private final CaptchaService captchaService;
    private final CentroService centroService;
    private final SeguridadClienteService seguridadClienteService;
    private final PacienteService pacienteService;
    private final TrxClient trxClient;

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private final int INTENTOS_RESTANTES_INDEFINIDO = -1;

    public UsuarioRequestDto login(String autorization, String captchaToken, boolean validarCaptcha, boolean useCryptoAES) {
        final String NOMBRE_METODO = String.format("%s:%s","login",autorization);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), formatter.format(new Date()));

        try {
            if (validarCaptcha) {
                this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"***WEB: Client IP"), seguridadClienteService.getClientIP());

                this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Captcha"), captchaToken);
                captchaService.process(captchaToken, CaptchaAction.LOGIN);
            }
            else {
                this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"***MOVIL: Client IP"), seguridadClienteService.getClientIP());
            }

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Acceso en bloqueos"), autorization);
            seguridadClienteService.verificarAcceso();

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Autorizacion"), autorization);
            String cipherText = autorization;
            String base64 = StringUtil.reverse(cipherText);

            if (useCryptoAES) {
                String decrypted = SecurityUtil.decrypt(base64);
                base64 = StringUtil.reverse(decrypted);

                this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Encriptado"), base64);
                this.validarEncriptado(base64,validarCaptcha);
            }

            byte[] credDecoded = Base64.getDecoder().decode(base64);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Formato de Credenciales"), credentials);
            this.validarCredentials(credentials, validarCaptcha);

            //Credentials = username:password
            final String[] values = credentials.split(":", 2);
            final String userName = values[0];
            final String clave = values[1];

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Caracteres"), credentials);
            this.validarCaracteres(userName,clave);

            //Autentica con nuestro servicio: essi-oauth
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Credenciales"), credentials);
            Map credential = getCredentials(userName, clave, validarCaptcha);

            if (validarCaptcha)
                captchaService.success();

            UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto();
            usuarioRequestDto.setCredenciales(credential);

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Paciente"), userName);
            PacienteDto paciente = this.getUsuario(userName);

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Paciente en ESSI"), paciente.toString());
            PacienteEssiDto pacienteEssi = this.getPacienteEssi(paciente);

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo Indicador de Cita"), pacienteEssi.toString());
            this.setIndCita(pacienteEssi);
            this.setIndPedirCita(pacienteEssi);

            pacienteEssi.setEmail(paciente.getEmail());
            pacienteEssi.setNumCelular(paciente.getNroCelular());
            pacienteEssi.setNumTelefono(paciente.getNroTelefonoFijo());

            EssiPacienteResponseDto essiPacienteDto = Util.objectToObject(EssiPacienteResponseDto.class,pacienteEssi);
            essiPacienteDto.setNombreCompleto(essiPacienteDto.getNombreCompleto());
            essiPacienteDto.setIndCam(essiPacienteDto.isCam());
            essiPacienteDto.setGenero(pacienteEssi.getCodGenero().equals("1") ? "M" : "F");
            usuarioRequestDto.setPaciente(essiPacienteDto);

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Return"), usuarioRequestDto.toString());
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), formatter.format(new Date()));

            //Actualizar 'cod_centro' en tabla 'paciente' (27/09/2023)
            var request = new UpdateCentroPacienteRequestDto(paciente.getIdPaciente(), essiPacienteDto.getCodCentro());
            trxClient.updateCentroPaciente(request);

            return usuarioRequestDto;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private void validarCaracteres(String username, String password) {
        if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password))
            throw new ServiceException("El Usuario y la Clave no pueden estar vacios.");

        if (!(StringUtil.hasLengthBetween(username,6,20)))
            throw new ServiceException(String.format("El Usuario debe tener almenos %s caracteres y %s como maximo.",6,20));

        if (!(StringUtil.hasLengthBetween(password, 6,25)))
            throw new ServiceException(String.format("La Contraseña debe tener almenos %s caracteres y %s como maximo.",6,25));

        /*
        solo se validara la nueva politica de seguridad en el registro

        String sVerifyPasswordResult = SecurityUtil.verifyPassword(password);
        if (sVerifyPasswordResult != null)
            throw new ServiceException(sVerifyPasswordResult);*/
    }

    private void validarIntentosRestantesMsg(String message) {
        int intentosRestantes = seguridadClienteService.obtenerIntentosRestantes();

        if (intentosRestantes == INTENTOS_RESTANTES_INDEFINIDO) {
            throw new ServiceException(message);
        }
        else {
            throw new ServiceException(String.format("%s (%s intento(s) restante(s))",message,intentosRestantes));
        }
    }

    private void validarIntentosRestantes() {
        int intentosRestantes = seguridadClienteService.obtenerIntentosRestantes();

        if (intentosRestantes == INTENTOS_RESTANTES_INDEFINIDO) {
            throw new ServiceException("Tu usuario o contraseña es incorrecta. intenta de nuevo o recupera tu contraseña");
            //throw new ServiceException("Usuario o contraseña incorrectos");
        }
        else {
            throw new ServiceException("Tu usuario o contraseña es incorrecta. intenta de nuevo o recupera tu contraseña");
            //throw new ServiceException(String.format("Usuario o contraseña incorrectos (%s intento(s) restante(s))",intentosRestantes));
        }
    }

    private void validarEncriptado(String base64, boolean validarCaptcha) {
        if (StringUtil.isNullOrEmpty(base64)) {

            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Login","encriptado-base64",base64,
                    null, null,"Error en las credenciales recibidas (encriptado invalido)");

            this.validarIntentosRestantes();

        }
    }

    private void validarCredentials(String credentials, boolean validarCaptcha) {
        if (StringUtil.isNullOrEmpty(credentials)) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Login","credentials",credentials,
                    null, null,"Error en las credenciales recibidas (base64 invalido)");

            this.validarIntentosRestantes();
        }

        if (!credentials.contains(":")) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Login","credentials",credentials,
                    null, null,"Error en las credenciales recibidas (base64 invalido)");

            this.validarIntentosRestantes();
        }
    }

    @SneakyThrows
    private void setIndCita(PacienteEssiDto pacienteEssiDto) {
        final String NOMBRE_METODO = String.format("%s:%s","setIndCita",pacienteEssiDto.getNumDoc());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), formatter.format(new Date()));

        //boolean applyCita = centroService.checkApplyCita(pacienteEssiDto.getCodCentro());
        //pacienteEssiDto.setIndicadorCita(applyCita);

        // VALIDACION - (1)
        RequestParametroDto requestParametroDto = new RequestParametroDto();
        requestParametroDto.setCodTipDoc(pacienteEssiDto.getCodTipoDoc());
        requestParametroDto.setNumDoc(pacienteEssiDto.getNumDoc());
        requestParametroDto.setCodCentro(pacienteEssiDto.getCodCentro());
        EssiResponseDto<ParametroSolicitudResponseDto> responseParametroDto = pacienteService.parametroSolicitud(requestParametroDto);
        boolean isServHospOk = false;

        if (responseParametroDto.getCodError().equals(EssiCode.SUCCESS)) {
            ParametroSolicitudResponseDto paramServHosp = responseParametroDto.getvDataItem();

            if (paramServHosp != null) {
                if (paramServHosp.getDataParmServicioHosp() != null) {
                    if (paramServHosp.getDataParmServicioHosp().length > 0) {
                        if (!StringUtil.isNullOrEmpty(paramServHosp.getDataParmServicioHosp()[0].getCodServicioHosp())) {
                            isServHospOk = true;
                        }
                    }
                }
            }
        }

        // VALIDACION - (2)
        Date fechaHoy = DateUtil.currentDate();
        Date fechaVigencia = DateUtil.stringToDate(pacienteEssiDto.getFecVigHasta(), DateFormat.DD_MM_YYYY);
        long diff = DateUtil.dateDiffInDays(fechaHoy,fechaVigencia);
        boolean isFechaVigenciaValida = (diff >= 0);

        int tipoAlerta = 0;

        if (isServHospOk) {
            if (pacienteEssiDto.getCodIndicadorAtencion().equals("2")) {
                tipoAlerta = 2; // Muestra mensaje "EPS"
            }
            if (!isFechaVigenciaValida) {
                tipoAlerta = 3; // Muestra mensaje "SEGURO VENCIDO"
            }
        }
        else {
            tipoAlerta = 1; // Muestra mensaje "Tu IPRESS NO DA CITAS"
        }

        pacienteEssiDto.setTipoAlerta(tipoAlerta);

        boolean isIndAtencion = pacienteEssiDto.getCodIndicadorAtencion().equals("1");
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), formatter.format(new Date()));
        pacienteEssiDto.setIndicadorCita(isFechaVigenciaValida && isServHospOk && isIndAtencion);
    }

    @SneakyThrows
    private void setIndPedirCita(PacienteEssiDto pacienteEssiDto) {
        final String NOMBRE_METODO = String.format("%s:%s","setIndPedirCita",pacienteEssiDto.getNumDoc());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), formatter.format(new Date()));

        CentroDto centro = centroService.getCentro(pacienteEssiDto.getCodCentro());
        //boolean isCentroRegistrado = (centro != null);
        //String codRed = isCentroRegistrado ? centro.getCodRed() : "000";

        //boolean puedePedirCita = StringUtil.isStringInMatcher(codRed, pedirCitaCodRedesHabilitadas);
        boolean puedePedirCita = false; //ahora sera segun el centro que tiene el registro de solicitud
        pacienteEssiDto.setIndPedirCita(puedePedirCita);

        //VALIDAR: indTeleUrgencia
        pacienteEssiDto.setIndTeleUrgencia(centro.isIndTeleUrgencia());

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), formatter.format(new Date()));
    }

    public PacienteDto getUsuario(String userName) {
        this.loggerInfo("Inicio getUsuario", formatter.format(new Date()));
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setNumeroDocIdent(userName);
        String urlPaciente = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PACIENTE_GET).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(pacienteDto, this.getHttpHeader());
        this.loggerInfo("URL getUsuario:", urlPaciente);
        ResponseEntity<ResponseDto> responsePaciente = restTemplate.exchange(urlPaciente, HttpMethod.POST, httpEntity,
                ResponseDto.class);
        if (responsePaciente.getBody().getData() == null) {
            this.validarIntentosRestantes();
            //throw new ServiceException("Paciente no registrado.");
        }
        this.loggerInfo("Fin getUsuario", formatter.format(new Date()));
        return Util.objectToObject(PacienteDto.class, responsePaciente.getBody().getData());
    }

    public PacienteEssiDto getPacienteEssi(PacienteDto pacienteDto) {
        this.loggerInfo("Inicio getPacienteEssi", formatter.format(new Date()));
        PacienteEssiDto pacienteEssiDto = new PacienteEssiDto();
        EssiPacienteRequestDto userLoginDto = new EssiPacienteRequestDto();
        userLoginDto.setCodOpcion(Constantes.COD_OPCION);
        userLoginDto.setCodTipDoc(pacienteDto.getTipoDocIdent());
        userLoginDto.setNumDoc(pacienteDto.getNumeroDocIdent());
        userLoginDto.setFecNacimiento(pacienteDto.getFechaNacimiento());

        String urlLogin = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_LOGIN_MOVIL)
                .build().encode().toUriString();

        HttpEntity<?> httpEntity = new HttpEntity<>(userLoginDto, this.getHttpHeader());
        this.loggerInfo("URL:", urlLogin);
        ResponseEntity<Map> responseEssi = restTemplate.exchange(urlLogin, HttpMethod.POST, httpEntity,
                Map.class);
        if (!responseEssi.getBody().get("codError").toString().equals(Constantes.RETORNO_EXITO)) {

            if (responseEssi.getBody().get("codError").toString().equals(EssiCode.DOCUMENTO_INACTIVO)) {
                this.validarIntentosRestantesMsg(EssiErrorMessage.DOCUMENTO_INACTIVO);
            }
            else {
                this.validarIntentosRestantes();
            }

            //throw new ServiceException("No se pudo obtener la información del Paciente.");
            //throw new ServiceException(responseEssi.getBody().get("desError").toString());
        }
        List<Map> lista = (List<Map>) responseEssi.getBody().get("vDataItem");
        for (Map itemMap : lista) {
            pacienteEssiDto = Util.objectToObject(PacienteEssiDto.class, itemMap);
        }
        this.loggerInfo("Fin getPacienteEssi", formatter.format(new Date()));
        return pacienteEssiDto;
    }

    private Map getCredentials(String userName, String clave, boolean validarCaptcha) {
        try {
            this.loggerInfo("Inicio getCredentials", formatter.format(new Date()));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", Constantes.URL_TOKEN_USER_SECURITY);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(Constantes.URL_GRANT_TYPE, Constantes.URL_ACCESS);
            body.add(Constantes.URL_USERNAME, userName);
            body.add(Constantes.URL_ACCESS, clave);

            String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_OAUTH))
                    .path(Constantes.URL_TOKEN).build().encode().toUriString();
            HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
            this.loggerInfo("URL getCredentials:", url);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                    Map.class);

            this.loggerInfo("Fin getCredentials", formatter.format(new Date()));
            return response.getBody();

        } catch (HttpStatusCodeException e) {

            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Login","Usuario",userName,
                    "Clave", clave,"Error en las credenciales, no son validas");

            this.validarIntentosRestantes();
            return null;
        }
    }
}