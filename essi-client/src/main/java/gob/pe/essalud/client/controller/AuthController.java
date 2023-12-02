package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.dto.usuario.UsuarioRequestDto;
import gob.pe.essalud.client.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController {
    private static final String LOGIN = "login";
    private static final String LOGIN_MOVIL = "lm";
    private static final String NOMBRE_METODO_FORMAT = "%s:%s";
    private static final String LOGGER_FORMAT = "[%s]: %s";
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(LOGIN)
    public ResponseEntity<ResponseDto> login(
            @RequestHeader("Authorization") String autorization,
            @RequestParam(required = false) String captchaToken) {
        final String NOMBRE_METODO = String.format(NOMBRE_METODO_FORMAT, "->login", autorization);
        this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Inicio"), "");
        ResponseDto<UsuarioRequestDto> response = loginGeneral(autorization, captchaToken, true, true);
        this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Fin"), "");
        return ResponseEntity.ok(response);
    }

    @PostMapping(LOGIN_MOVIL)
    public ResponseEntity<ResponseDto> loginMovil(
            @RequestHeader("Authorization") String autorization,
            @RequestParam(required = false) String captchaToken) {
        final String NOMBRE_METODO = String.format(NOMBRE_METODO_FORMAT, "->loginMovil", autorization);
        this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Inicio"), "");
        ResponseDto<UsuarioRequestDto> response = loginGeneral(autorization, captchaToken, false, false);
        this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Fin"), "");
        return ResponseEntity.ok(response);
    }

    private ResponseDto<UsuarioRequestDto> loginGeneral(String autorization, String captchaToken,
            boolean validarCaptcha, boolean useCryptoAES) {
        final String NOMBRE_METODO = String.format(NOMBRE_METODO_FORMAT, "->loginGeneral", autorization);
        ResponseDto<UsuarioRequestDto> response = new ResponseDto<>();
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto();
        try {
            usuarioRequestDto = authService.login(autorization, captchaToken, validarCaptcha, useCryptoAES);
            usuarioRequestDto.setSuccessLogin(Boolean.TRUE);
            this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Success"), "true");
        } catch (Exception ex) {
            this.loggerDebug(String.format(LOGGER_FORMAT, NOMBRE_METODO, "Exception"), ex.getMessage());
            response.setCodResult(ResponseType.VALIDATION);
            response.setMessage(ex.getMessage());
            usuarioRequestDto.setSuccessLogin(Boolean.FALSE);
        }
        response.setData(usuarioRequestDto);
        return response;
    }
}