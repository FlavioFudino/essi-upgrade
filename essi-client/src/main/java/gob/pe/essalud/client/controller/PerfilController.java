package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.usuario.UsuarioPerfilDto;
import gob.pe.essalud.client.service.PerfilService;
import gob.pe.essalud.client.service.ServiceException;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;

@RestController
@RequestMapping(PerfilController.PERFIL)
public class PerfilController extends BaseController {

    public static final String PERFIL = "perfil";
    public static final String FICHA = "ficha";
    private final PerfilService perfilService;
    private final SessionService session;

    @Autowired
    public PerfilController(PerfilService perfilService,
                            SessionService session) {
        this.perfilService = perfilService;
        this.session = session;
    }

    /*
    @PutMapping(FICHA)
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody FichaDto model) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "save");
        model.setTipoDocIdent(session.getTipoDocumento());
        model.setNumDocIdent(session.getNumeroDocumento());
        ResponseDto<FichaDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        FichaDto data = perfilService.saveFicha(model);
        response.setData(data);
        return new ResponseEntity<>(response, httpStatus);
    }*/

    @PutMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody UsuarioPerfilDto usuarioPerfilDto) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "save");
        usuarioPerfilDto.setTipoDocIdent(session.getTipoDocumento());
        usuarioPerfilDto.setNumeroDocIdent(session.getNumeroDocumento());
        ResponseDto<PacienteDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            PacienteDto data = perfilService.save(usuarioPerfilDto);
            response.setData(data);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        } catch (HttpStatusCodeException e) {
            String responseErrorServer = e.getResponseBodyAsString();
            response.setMessage(responseErrorServer);
            response.setCodResult(ResponseType.VALIDATION);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping
    public UsuarioPerfilDto get() {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->get");
        String tipoDocumento = session.getTipoDocumento();
        String numeroDocumento = session.getNumeroDocumento();
        return perfilService.get(tipoDocumento, numeroDocumento);
    }

}
