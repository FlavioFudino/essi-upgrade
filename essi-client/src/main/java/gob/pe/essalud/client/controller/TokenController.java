package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.SessionService;
import gob.pe.essalud.client.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("")
public class TokenController extends BaseController {
    private final TokenService tokenService;
    private final SessionService session;

    private static final String TOKEN = "token";
    private static final String RETOKEN = "retoken";
    private static final String SENDTOKEN = "sendToken";
    private static final String TOKEN_REGISTRO = "token-registro";
    private static final String TOKEN_ACTIVAR = "token-registro/activar";

    @Autowired
    public TokenController(TokenService tokenService,
                           SessionService session) {
        this.tokenService = tokenService;
        this.session = session;
    }

    /*
    @PostMapping(value = TOKEN)
    public ResponseEntity<Map> token(@RequestHeader("Authorization") String autorization) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->token");
        return ResponseEntity.ok(tokenService.token(autorization));

    }*/

    @PostMapping(value = SENDTOKEN)
    protected Map sendToken(@Valid @RequestBody Map paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->sendToken");
        paramInput.put("tipoDocIdent", session.getTipoDocumento());
        paramInput.put("numeroDocIdent", session.getNumeroDocumento());
        return tokenService.sendToken(paramInput);
    }

    @PostMapping(value = RETOKEN)
    public ResponseEntity<Map> retoken(@RequestBody Map token) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->retoken");
        return ResponseEntity.ok(tokenService.retoken(token));
    }

    /*
    @PostMapping(value = TOKEN_REGISTRO)
    public ResponseEntity<Map> tokenRegistro(@Valid @RequestBody TokenRegistroRequestDto body) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->tokenRegistro");
        body.setUsername(session.getNumeroDocumento());
        return ResponseEntity.ok(tokenService.tokenRegistro(body));
    }

    @PostMapping(value = TOKEN_ACTIVAR)
    public ResponseEntity<Map> tokenActivar(@RequestBody TokenRegistroRequestDto body) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->tokenActivar");
        body.setUsername(session.getNumeroDocumento());
        return ResponseEntity.ok(tokenService.tokenActivar(body));
    }*/

}
