package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.PacienteTokenDto;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("token")
public class TokenController extends BaseController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "sendToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendToken(@Valid @RequestBody(required = true) PacienteTokenDto paciente) {
        ResponseDto<PacienteTokenDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            tokenService.sendToken(paciente);
            response.setData(paciente);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "sendCitaNotificationEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendCitaNotificationEmail(@Valid @RequestBody(required = true) PacienteTokenDto paciente) {
        ResponseDto<PacienteTokenDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            //tokenService.sendToken(paciente);
            //response.setData(paciente);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /*
    @PostMapping(value = "notificacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> notificacion() {
        List<NotificacionTokenDto> response = new ArrayList<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            response = tokenService.notificacionList();
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }*/

}
