package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.common.http.ResponseType;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;
import gob.pe.essalud.trx.dto.TokenRequestDto;
import gob.pe.essalud.trx.service.TokenRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TokenRegistroController.TOKEN_REGISTRO)
public class TokenRegistroController extends BaseController {

    public static final String TOKEN_REGISTRO = "token-registro";

    private static final String ACTIVAR = "activar";
    private static final String VALIDAR = "validar";
    private static final String EXISTE_ACTIVO = "existe-activo";

    private final TokenRegistroService tokenRegistroService;

    @Autowired
    public TokenRegistroController(TokenRegistroService tokenAccesoService) {
        this.tokenRegistroService = tokenAccesoService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TokenRegistroRequestDto tokenRequest) {
        tokenRegistroService.generarToken(tokenRequest, true);
        ResponseDto response = new ResponseDto<>();
        response.setCodResult(ResponseType.SUCCESS);
        response.setData(tokenRequest.getTipo());
        return ResponseEntity.ok(response);
    }

    @PostMapping(VALIDAR)
    public ResponseEntity<?> validarToken(@RequestBody TokenRegistroRequestDto tokenRequest) {
        boolean result = tokenRegistroService.validarToken(tokenRequest);

        int codResult = ResponseType.VALIDATION;
        if (result)
            codResult = ResponseType.SUCCESS;

        ResponseDto response = new ResponseDto<>();
        response.setCodResult(codResult);
        response.setData(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping(EXISTE_ACTIVO)
    public ResponseEntity<?> existeTokenActivo(@RequestBody TokenRegistroRequestDto tokenRequest) {
        boolean result = tokenRegistroService.existeTokenActivo(tokenRequest);

        int codResult = ResponseType.VALIDATION;
        if (result)
            codResult = ResponseType.SUCCESS;

        ResponseDto response = new ResponseDto<>();
        response.setData(result);
        response.setCodResult(codResult);

        return ResponseEntity.ok(response);
    }

    @PostMapping(ACTIVAR)
    public ResponseEntity<?> activarToken(@RequestBody TokenRegistroRequestDto tokenRequest) {
        tokenRegistroService.activar(tokenRequest, true);
        ResponseDto response = new ResponseDto<>();
        response.setCodResult(ResponseType.SUCCESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getToken(@RequestParam("token") String token) {
        ResponseDto<TokenRequestDto> response = new ResponseDto<>();
        TokenRequestDto tokenRecovery = tokenRegistroService.getTokenRecovery(token);
        response.setData(tokenRecovery);
        return ResponseEntity.ok(response);
    }

}
