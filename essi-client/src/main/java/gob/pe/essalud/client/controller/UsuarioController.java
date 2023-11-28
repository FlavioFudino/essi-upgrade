package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.usuario.UsuarioRegisterDto;
import gob.pe.essalud.client.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(UsuarioController.USUARIOS)
public class UsuarioController extends BaseController {

    public static final String USUARIOS = "usuarios";
    private static final String REGISTRAR_MOVIL = "rm";

    private static final String VALIDAR = "valid";
    private static final String VALIDAR_MOVIL = "vm";

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody UsuarioRegisterDto model,
                                            @RequestParam(required = false) String captchaToken) {

        final String NOMBRE_METODO = String.format("%s:%s","->registrar",model.toString());
        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"),"");

        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            Map resultado = usuarioService.save(model, captchaToken,true);
            response.setData(true);
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Success"),"true");
        } catch (HttpStatusCodeException e) {
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Exception"),e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Fin"),"");
        return ResponseEntity.ok(response);
    }

    @PostMapping(REGISTRAR_MOVIL)
    public ResponseEntity<ResponseDto<Integer>> saveMovil(@Valid @RequestBody UsuarioRegisterDto model,
                                            @RequestParam(required = false) String captchaToken) {

        final String NOMBRE_METODO = String.format("%s:%s","->registrarMovil",model.toString());
        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"),"");

        ResponseDto<Integer> response = new ResponseDto<>();
        try {
            Map resultado = usuarioService.save(model, captchaToken,false);
            response.setData(1);
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Success"),"true");
        } catch (HttpStatusCodeException e) {
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Exception"),e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Fin"),"");
        return ResponseEntity.ok(response);
    }

    @PostMapping(VALIDAR)
    public ResponseEntity<ResponseDto<Integer>> valid(@Valid @RequestBody UsuarioRegisterDto model,
                                             @RequestParam(required = false) String captchaToken) {

        final String NOMBRE_METODO = String.format("%s:%s","->validarRegistrar",model.toString());
        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"),"");

        ResponseDto<Integer> response = new ResponseDto<>();
        try {
            Map resultado = usuarioService.valid(model, captchaToken,true);
            response.setData(Integer.parseInt(resultado.get("data").toString()));
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Success"),"true");
        } catch (HttpStatusCodeException e) {
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Exception"),e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Fin"),"");
        return ResponseEntity.ok(response);
    }

    @PostMapping(VALIDAR_MOVIL)
    public ResponseEntity<ResponseDto<Integer>> validMovil(@Valid @RequestBody UsuarioRegisterDto model,
                                             @RequestParam(required = false) String captchaToken) {

        final String NOMBRE_METODO = String.format("%s:%s","->validarRegistrarMovil",model.toString());
        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"),"");

        ResponseDto<Integer> response = new ResponseDto<>();
        try {
            Map resultado = usuarioService.valid(model, captchaToken,false);
            response.setData(Integer.parseInt(resultado.get("data").toString()));
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Success"),"true");
        } catch (HttpStatusCodeException e) {
            this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Exception"),e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.loggerDebug(String.format("[%s]: %s",NOMBRE_METODO,"Fin"),"");
        return ResponseEntity.ok(response);
    }

}
