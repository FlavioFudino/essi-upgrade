package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.*;
import gob.pe.essalud.trx.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UsuarioController.USUARIOS)
public class UsuarioController extends BaseController {

    public static final String USUARIOS = "usuarios";
    public static final String PERFIL = "perfil";
    public static final String BUSQ_ACTIVA = "busq-activa";
    public static final String EXIST = "exist";

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UsuarioRegisterDto model) {
        ResponseDto<UsuarioDto> response = new ResponseDto<>();
        UsuarioDto userRegistered = usuarioService.save(model);
        response.setData(userRegistered);
        return ResponseEntity.ok(response);
    }

    @PutMapping(PERFIL)
    public ResponseEntity<?> updatePerfil(@Valid @RequestBody UsuarioPerfilDto model) {
        ResponseDto<PacienteDto> response = new ResponseDto<>();
        PacienteDto userRegistered = usuarioService.updatePerfil(model);
        response.setData(userRegistered);
        return ResponseEntity.ok(response);
    }

    /**
     * Expone el servicio que actualiza un paciente existente.
     *
     * @param model
     * @return
     */
    @PostMapping(BUSQ_ACTIVA)
    public ResponseEntity<?> updateBusqActiva(@Valid @RequestBody AseguradoRequestDto model) {
        PacienteDto userRegistered = usuarioService.updateBusqActiva(model);
        return ResponseEntity.ok(userRegistered);
    }

    @GetMapping(PERFIL)
    public UsuarioPerfilDto getPerfil(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        return usuarioService.getPerfil(tipoDocumento, numeroDocumento);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String username) {
        ResponseDto<UsuarioOauthDto> response = new ResponseDto<>();
        UsuarioOauthDto userRegistered = usuarioService.get(username);
        response.setData(userRegistered);
        return ResponseEntity.ok(response);
    }

    @GetMapping(EXIST)
    public ResponseEntity<?> exist(@RequestParam String username) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        Boolean userRegistered = usuarioService.existVigente(username);
        response.setData(userRegistered);
        return ResponseEntity.ok(response);
    }

}
