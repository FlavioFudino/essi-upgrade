package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.client.EmailServiceClient;
import gob.pe.essalud.trx.client.SmsClient;
import gob.pe.essalud.trx.common.constants.Constantes;
import gob.pe.essalud.trx.common.constants.EstadoUsuario;
import gob.pe.essalud.trx.common.constants.TokenRegistro;
import gob.pe.essalud.trx.common.util.PropertiesUtil;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.TokenRegistroDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;
import gob.pe.essalud.trx.dto.TokenRequestDto;
import gob.pe.essalud.trx.dto.emailservice.RecuperarClaveMobileRequestDto;
import gob.pe.essalud.trx.dto.emailservice.RecuperarClaveWebRequestDto;
import gob.pe.essalud.trx.dto.emailservice.RegistrarUsuarioRequestDto;
import gob.pe.essalud.trx.dto.smsservice.SmsSendResponseDto;
import gob.pe.essalud.trx.jpa.model.TokenRegistroModel;
import gob.pe.essalud.trx.jpa.model.UsuarioModel;
import gob.pe.essalud.trx.jpa.repository.TokenRegistroRepository;
import gob.pe.essalud.trx.jpa.repository.UsuarioRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.repository.TokenRegistroMyRepository;
import gob.pe.essalud.trx.service.TokenRegistroService;
import gob.pe.essalud.trx.util.DateUtil;
import gob.pe.essalud.trx.util.StringUtil;
import gob.pe.essalud.trx.validators.TokenRegistroValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenRegistroServiceImpl extends BaseService implements TokenRegistroService {
    private final TokenRegistroRepository tokenRegistroRepository;
    private final ParametroRepository parametroRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenRegistroMyRepository tokenRegistroMyRepository;

    private final TokenRegistroValidator tokenRegistroValidator;
    private final EmailServiceClient emailServiceClient;
    private final SmsClient smsClient;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${sms-auth-key}")
    private String smsAuthKey;

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    public TokenRegistroServiceImpl(TokenRegistroRepository tokenRegistroRepository,
                                    ParametroRepository parametroRepository,
                                    TokenRegistroValidator tokenRegistroValidator,
                                    UsuarioRepository usuarioRepository,
                                    TokenRegistroMyRepository tokenRegistroMyRepository,
                                    EmailServiceClient emailServiceClient,
                                    SmsClient smsClient) {
        this.tokenRegistroRepository = tokenRegistroRepository;
        this.parametroRepository = parametroRepository;
        this.usuarioRepository = usuarioRepository;
        this.tokenRegistroValidator = tokenRegistroValidator;
        this.tokenRegistroMyRepository = tokenRegistroMyRepository;
        this.emailServiceClient = emailServiceClient;
        this.smsClient = smsClient;
    }

    @Override
    public TokenRegistroDto generarTokenRecovery(TokenRegistroRequestDto request) {
        final String NOMBRE_METODO = String.format("%s:%s","generarTokenRecovery",request.toString());

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), dateFormat.format(new Date()));

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo la fecha actual"), "");
        Date fechaActual = parametroRepository.getFecha();

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Generando codigo de 4 digitos o token para el enlace de recuperacion"), "");
        String token = generateNewToken(request.getOrigin());
        Date fechaExpiracion = DateUtil.addMinutes(fechaActual, 15);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo modelo de token registro"), "");
        TokenRegistroModel tokenRegistroModel = getTokenRegistroModel(request, fechaActual, token, fechaExpiracion);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Guardando token registro"), "");
        tokenRegistroRepository.save(tokenRegistroModel);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo propiedades de url de recuperacion de clave"), "");
        String urlBase = propertiesUtil.getPropertiesString(Constantes.URL_RECOVERY_PASSWORD_ENDPOINT);
        String resource = propertiesUtil.getPropertiesString(Constantes.URL_RECOVERY_PASSWORD_PATH);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Generando URL de recuperacion"), "");
        String url = UriComponentsBuilder
                .fromUriString(urlBase + resource)
                .build().encode().toUriString();
        url += "?token=" + token;
        //url += "&username=" + request.getUsername();
        String correo = request.getCorreo();
        String message = "";

        if (request.getOrigin().equals(Constantes.URL_RECOVERY_ORIGIN_APK)) {
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviando correo (movil)"), correo);

            RecuperarClaveMobileRequestDto requestMobileDto = new RecuperarClaveMobileRequestDto();
            requestMobileDto.setEmail(correo);
            requestMobileDto.setToken(token);
            emailServiceClient.recuperarClaveMobile(requestMobileDto);
        }
        else {
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviando correo (web)"), correo);

            RecuperarClaveWebRequestDto requestWebDto = new RecuperarClaveWebRequestDto();
            requestWebDto.setEmail(correo);
            requestWebDto.setUrl(url);
            emailServiceClient.recuperarClaveWeb(requestWebDto);
        }

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Return"), tokenRegistroModel.toString());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), dateFormat.format(new Date()));
        return Util.objectToObject(TokenRegistroDto.class, tokenRegistroModel);
    }

    private static String generateNewToken(String origin) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        if (origin.equals(Constantes.URL_RECOVERY_ORIGIN_APK))
            return StringUtil.getRandomNumber(4);
        else
            return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public TokenRegistroDto generarToken(TokenRegistroRequestDto request, boolean validarUsuario) {
        final String NOMBRE_METODO = String.format("%s:%s","generarToken",request.toString());

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), dateFormat.format(new Date()));

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo la fecha actual"), "");
        Date fechaActual = parametroRepository.getFecha();

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Generando codigo de 4 digitos"), "");
        String token = StringUtil.getRandomNumber(4);
        Date fechaExpiracion = DateUtil.addMinutes(fechaActual, 15);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo modelo de token registro"), "");
        TokenRegistroModel tokenRegistroModel = getTokenRegistroModel(request, fechaActual, token, fechaExpiracion);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Guardando token registro"), "");
        tokenRegistroRepository.save(tokenRegistroModel);

        if (request.getTipo().equals(TokenRegistro.CONFIRMAR_REGISTRO_EMAIL)) {

            String correo = request.getCorreo();

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviando correo"), correo);

            RegistrarUsuarioRequestDto requestDto = new RegistrarUsuarioRequestDto();
            requestDto.setEmail(correo);
            requestDto.setToken(token);
            emailServiceClient.registrarUsuario(requestDto);

        } else if (request.getTipo().equals(TokenRegistro.CONFIRMAR_REGISTRO_SMS)) {

            String authorization = "Basic ".concat(smsAuthKey);
            String mobile = request.getNumCelular();
            String country = "51"; //PE = 51
            String message = "ESSALUD-MiConsulta: Su codigo de registro es ".concat(token);
            String messageFormat = "1"; //1 = Normal

            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviando SMS"), mobile);

            SmsSendResponseDto smsResponse = smsClient.send(authorization,mobile,country,message,messageFormat);
        }

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Return"), tokenRegistroModel.toString());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), dateFormat.format(new Date()));
        return Util.objectToObject(TokenRegistroDto.class, tokenRegistroModel);
    }

    private TokenRegistroModel getTokenRegistroModel(TokenRegistroRequestDto request, Date fechaActual, String token, Date fechaExpiracion) {
        TokenRegistroModel tokenRegistroModel = new TokenRegistroModel();
        tokenRegistroModel.setDateCreate(fechaActual);
        tokenRegistroModel.setDateExpiration(fechaExpiracion);
        tokenRegistroModel.setToken(token);
        tokenRegistroModel.setCorreo(request.getCorreo());
        tokenRegistroModel.setIdUsuario(request.getIdUsuario());
        tokenRegistroModel.setTipo(request.getTipo());
        return tokenRegistroModel;
    }

    @Override
    public void activar(TokenRegistroRequestDto request, Boolean validUser) {
        this.loggerInfo("Inicio activar", dateFormat.format(new Date()));
        TokenRegistroDto tokenRegistro = tokenRegistroValidator.validateActivation(request, validUser);

        // Actualizar confirmación token
        TokenRegistroModel tokenRegistroModel = tokenRegistroRepository.getOne(tokenRegistro.getIdTokenRegistro());
        tokenRegistroModel.setIndConfirmado(true);
        tokenRegistroRepository.save(tokenRegistroModel);

        // Actualizar estado usuario
        UsuarioModel solicitudModel = usuarioRepository.getOne(request.getIdUsuario());
        solicitudModel.setEstado(EstadoUsuario.ACTIVO);
        usuarioRepository.save(solicitudModel);
        this.loggerInfo("Fin activar", dateFormat.format(new Date()));
    }

    @Override
    public boolean validarToken(TokenRegistroRequestDto tokenRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("correo", tokenRequest.getCorreo());
        params.put("codigo", tokenRequest.getToken());
        params.put("tipo", tokenRequest.getTipo().toString());
        Integer result = this.tokenRegistroMyRepository.getValidarToken(params);
        if (result == null)
            result = 0;

        return (result > 0);
    }

    @Override
    public boolean existeTokenActivo(TokenRegistroRequestDto tokenRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("correo", tokenRequest.getCorreo());
        params.put("tipo", tokenRequest.getTipo().toString());
        Integer result = this.tokenRegistroMyRepository.getExisteTokenActivo(params);
        if (result == null)
            result = 0;

        return (result > 0);
    }

    public TokenRegistroDto generarToken(TokenRegistroRequestDto request) {
        return generarToken(request, false);
    }

    @Override
    public TokenRequestDto getTokenRecovery(String token) {
        /*this.loggerInfo("Inicio getTokenRecovery", dateFormat.format(new Date()));
        TokenRegistroModel tokenRegistroModel = tokenRegistroRepository.findTopByTokenOrderByDateCreateDesc(token).orElse(null);
        if (tokenRegistroModel == null)
            throw new ServiceException("El token no se encuentra registrado");
        UsuarioModel userModel = usuarioRepository.getOne(tokenRegistroModel.getIdUsuario());
        TokenRequestDto tokenRequestDto = Util.objectToObject(TokenRequestDto.class, tokenRegistroModel);
        tokenRequestDto.setUserName(userModel.getUsername());
        this.loggerInfo("Fin getTokenRecovery", dateFormat.format(new Date()));
        return tokenRequestDto;*/
        return null;
    }
}
